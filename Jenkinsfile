def label = "mypod-${UUID.randomUUID().toString()}"
def serviceaccount = "jenkins-admin"
podTemplate(label: label, serviceAccount: serviceaccount,
containers: [containerTemplate(name: 'maven', image: 'maven:3-jdk-8', ttyEnabled: true, command: 'cat'),
			  containerTemplate(name: 'jq', image: 'stedolan/jq', ttyEnabled: true, command: 'cat'),
              containerTemplate(name: 'tomcat', image: 'tomcat', ttyEnabled: true, command: 'cat'),
			  containerTemplate(name: 'kubectl', image: 'localhost:32121/root/docker_registry/aiindevops.azurecr.io/docker-kubectl:19.03-alpine', ttyEnabled: true, command: 'cat'),
			  containerTemplate(name: 'docker', image: 'docker:1.13', ttyEnabled: true, command: 'cat',envVars: [
            envVar(key: 'DOCKER_SOCK', value: "$DOCKER_SOCK"),envVar(key: 'DOCKER_HOST', value: "$DOCKER_HOST")])],
			volumes: [hostPathVolume(hostPath: "$DOCKER_SOCK", mountPath: "$DOCKER_SOCK")],
			imagePullSecrets: ['gcrcred']
			)
			  
{
	node(label) {
		def GIT_URL="http://gitlab.ethan.svc.cluster.local:8084/gitlab/root/lkmgft_cs_employeereferral.git"
		def GIT_CREDENTIAL_ID ='gitlab'
		def GIT_BRANCH='master'
		def deploy_env="${deploy_env}"
		def SONAR_HOST_URL='http://sonar.ethan.svc.cluster.local:9001/sonar'
		def GCR_HUB_ACCOUNT = 'localhost:32121'
		stage('checkout')
			{
				checkout([$class: 'GitSCM', branches: [[name: "${GIT_BRANCH}"]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: '.']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${GIT_CREDENTIAL_ID}", url: "${GIT_URL}"]]])
				committerEmail = sh (
      			script: 'git --no-pager show -s --format=\'%ae\'',
      			returnStdout: true
				).trim()
				echo "$committerEmail"
				sh '''
				committerEmail='''+ committerEmail +'''
				echo "committerEmail is $committerEmail"
				'''
				 sh '''
				  GIT_NAME=$(git --no-pager show -s --format=\'%an\' $GIT_COMMIT)
				  echo "GIT_NAME is ${GIT_NAME}"
				  '''

			wrap([$class: 'BuildUser']) 
		{
			sh 'echo ${BUILD_USER}'
			sh '''
			committerEmail='''+ committerEmail +'''
			BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
			echo ${BUILDUSER}
		    sed -i "s/builduser/${BUILDUSER}/g" tomcat.yaml
		    sed -i "s/builduser/${BUILDUSER}/g" tomcat-svc.yaml
            sed -i "s/builduser/${BUILDUSER}/g" namespace.yaml
            sed -i "s/builduser/${BUILDUSER}/g" mysql.yaml
            sed -i "s/builduser/${BUILDUSER}/g" mysql-pvc.yaml
		    '''
		}
			}
			stage('build')
			{
				container('maven')
				{
					sh '''
					java -version
					mvn clean package -DskipTests
					'''
				}
			}
			stage('unit tests')
			{
				container('maven')
				{
					sh '''
					mvn test
					'''
				}
			}
			stage('SonarQube Analysis') {
			withCredentials([usernamePassword(credentialsId: 'SONAR', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]){
			//withSonarQubeEnv('SonarQube') {
			
		
				withSonarQubeEnv('SonarQube') {
                println('Sonar Method enter');
				def scannerHome = tool 'Sonar Scanner';
				sh "${scannerHome}/bin/sonar-scanner -Dsonar.login=$USERNAME -Dsonar.password=$PASSWORD";
                echo "Access the SonarQube URL from the Platform Dashboard tile"
				              
				}
				
			}
			}
		if ( "${deploy_env}" == "dev")
		{			
        stage('deploy to dev')
		{
			container('docker')
			{
				sh ("docker build -t localhost:32121/root/docker_registry/java:${deploy_env} .")
				withCredentials([[$class: 'UsernamePasswordMultiBinding',
                credentialsId: 'gitlab',
                usernameVariable: 'DOCKER_HUB_USER',
                passwordVariable: 'DOCKER_HUB_PASSWORD']]) {
                    sh ('docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASSWORD} '+GCR_HUB_ACCOUNT )
				sh ("docker push localhost:32121/root/docker_registry/java:${deploy_env}")
			}
			}
			container('kubectl'){
				sh ("sed -i 's/tomcat-deployenv/tomcat${deploy_env}/g' tomcat.yaml")
				sh ("sed -i 's/deployenv/${deploy_env}/g' tomcat.yaml")
				sh ("sed -i 's/tomcat-deployenv/tomcat${deploy_env}/g' tomcat-svc.yaml")
              	sh ("sed -i 's/buildenv/mysql${deploy_env}db/g' mysql-pvc.yaml")
              	sh ("sed -i 's/buildenv/mysql${deploy_env}/g' mysql.yaml")
                sh("sed -i 's/builddb/mysql${deploy_env}db/g' mysql.yaml")
              	sh("kubectl apply -f namespace.yaml")
				
				wrap([$class: 'BuildUser']) 
				{
					sh '''
				committerEmail='''+ committerEmail +'''
				BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
				kubectl get secret -n ${BUILDUSER} >> nodcred.txt
				'''
			   sh '''
				if grep -q gcrcred nodcred.txt; then
				echo "gcrcred found " >> pullsecret.txt
				else
			    echo "gcrcred not found "		
				fi
				'''
                if (fileExists('pullsecret.txt'))
		  {
			   echo "gcrcred already exists"
		  }
		  else
		  {
			  echo "gcrcred does not exists"
			   withCredentials([[$class: 'UsernamePasswordMultiBinding',
                credentialsId: 'gitlab',
                usernameVariable: 'DOCKER_HUB_USER',
                passwordVariable: 'DOCKER_HUB_PASSWORD']]) {
					
					
					sh '''
					committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
					kubectl create secret docker-registry gcrcred --docker-server=localhost:32121 --docker-username=${DOCKER_HUB_USER} --docker-password=${DOCKER_HUB_PASSWORD} -n ${BUILDUSER}
					'''
				}
		  }
				
                  try{
                    sh '''
                    committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
                    kubectl get deployment/mysql${deploy_env} -n ${BUILDUSER}
                              '''
                    if(true)
                    {
                      echo "mysql exits"
                    }
                  }
                  catch(e)
                  {
                    sh '''
                    committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
                    kubectl apply -f mysql-pvc.yaml
                    kubectl apply -f mysql.yaml
					sleep 120
				    POD=$(kubectl get pod -l app=mysqldev -n "${BUILDUSER}" -o jsonpath="{.items[0].metadata.name}")
					echo ${POD}
					kubectl exec -i "${POD}" -n "${BUILDUSER}" -- mysql -u root -ppassword < empreferal.sql
					
					'''
					
                    
                  }
				try{
				
				sh '''
				committerEmail='''+ committerEmail +'''
				BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
				kubectl get deployment/tomcat${deploy_env} -n ${BUILDUSER}
				'''
				
				if(true)
				{
					sh '''
					committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
                    kubectl delete deployment tomcatdev -n ${BUILDUSER}
				    kubectl apply -f tomcat.yaml
					'''
				}
				}
				catch(e)
				{
                sh("kubectl apply -f tomcat.yaml")
				sh("kubectl apply -f tomcat-svc.yaml")
                  
				sh 'sleep 60'
				sh '''
				committerEmail='''+ committerEmail +'''
				BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
				kubectl get svc tomcat${deploy_env} -n ${BUILDUSER}
				'''
				}
				LB = sh (returnStdout: true, script: ''' committerEmail='''+ committerEmail +''' ; BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`;kubectl get svc tomcat${deploy_env} -n ${BUILDUSER} -o jsonpath="{ .status.loadBalancer.ingress[*]['ip', 'hostname']}" ''')
				 echo "LB: ${LB}"
				def loadbalancer = "http://"+LB
				echo "application_url: ${loadbalancer}:8080/1003_CS_EmployeeReferral-0.0.1-SNAPSHOT"
				sleep 40
				}
				
			}
		}
		stage('function testing')
		{
			echo "functional testing"
			
		}
		}
		else
		{
		stage('deploy to dev')
		{
			echo "dev is not selected"
		}
		}
		
		if ( "${deploy_env}" == "staging")
		{			
        stage('deploy to staging')
		{
			container('docker')
			{
				sh ("docker build -t localhost:32121/root/docker_registry/java:${deploy_env} .")
				withCredentials([[$class: 'UsernamePasswordMultiBinding',
                credentialsId: 'gitlab',
                usernameVariable: 'DOCKER_HUB_USER',
                passwordVariable: 'DOCKER_HUB_PASSWORD']]) {
                    sh ('docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASSWORD} '+GCR_HUB_ACCOUNT )
				sh ("docker push localhost:32121/root/docker_registry/java:${deploy_env}")
			}
			}
			container('kubectl'){
				sh ("sed -i 's/tomcat-deployenv/tomcat${deploy_env}/g' tomcat.yaml")
				sh ("sed -i 's/deployenv/${deploy_env}/g' tomcat.yaml")
				sh ("sed -i 's/tomcat-deployenv/tomcat${deploy_env}/g' tomcat-svc.yaml")
              	sh ("sed -i 's/buildenv/mysql${deploy_env}db/g' mysql-pvc.yaml")
              	sh ("sed -i 's/buildenv/mysql${deploy_env}/g' mysql.yaml")
                sh("sed -i 's/builddb/mysql${deploy_env}db/g' mysql.yaml")
              	sh("kubectl apply -f namespace.yaml")
				
				wrap([$class: 'BuildUser']) 
				{
					sh '''
				committerEmail='''+ committerEmail +'''
				BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
				kubectl get secret -n ${BUILDUSER} >> nodcred.txt
				'''
			   sh '''
				if grep -q gcrcred nodcred.txt; then
				echo "gcrcred found " >> pullsecret.txt
				else
			    echo "gcrcred not found "		
				fi
				'''
                if (fileExists('pullsecret.txt'))
		  {
			   echo "gcrcred already exists"
		  }
		  else
		  {
			  echo "gcrcred does not exists"
			   withCredentials([[$class: 'UsernamePasswordMultiBinding',
                credentialsId: 'gitlab',
                usernameVariable: 'DOCKER_HUB_USER',
                passwordVariable: 'DOCKER_HUB_PASSWORD']]) {
					
					
					sh '''
					BUILDUSER='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
					kubectl create secret docker-registry gcrcred --docker-server=localhost:32121 --docker-username=${DOCKER_HUB_USER} --docker-password=${DOCKER_HUB_PASSWORD} -n ${BUILDUSER}
					'''
				}
		  }
				
                  try{
                    sh '''
                    committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
                    kubectl get deployment/mysql${deploy_env} -n ${BUILDUSER}
                              '''
                    if(true)
                    {
                      echo "mysql exits"
                    }
                  }
                  catch(e)
                  {
                    sh '''
                    BUILDUSER1=`echo ${BUILD_USER} | sed 's/ //g'`
					committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
                    kubectl apply -f mysql-pvc.yaml
                    kubectl apply -f mysql.yaml
					sleep 120
				    POD=$(kubectl get pod -l app=mysqlstaging -n "${BUILDUSER}" -o jsonpath="{.items[0].metadata.name}")
					echo ${POD}
					kubectl exec -i "${POD}" -n "${BUILDUSER}" -- mysql -u root -ppassword < empreferal.sql
					'''
					
                    
                  }
				try{
				
				sh '''
				committerEmail='''+ committerEmail +'''
				BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
				kubectl get deployment/tomcat${deploy_env} -n ${BUILDUSER}
				'''
				
				if(true)
				{
					sh '''
					committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
                    kubectl delete deployment tomcatdev -n ${BUILDUSER}
				    kubectl apply -f tomcat.yaml
					'''
				}
				}
				catch(e)
				{
                sh("kubectl apply -f tomcat.yaml")
				sh("kubectl apply -f tomcat-svc.yaml")
                  
				sh 'sleep 60'
				sh '''
				committerEmail='''+ committerEmail +'''
				BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
				kubectl get svc tomcat${deploy_env} -n ${BUILDUSER}
				'''
				}
				LB = sh (returnStdout: true, script: ''' committerEmail='''+ committerEmail +''' ; BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`;kubectl get svc tomcat${deploy_env} -n ${BUILDUSER} -o jsonpath="{ .status.loadBalancer.ingress[*]['ip', 'hostname']}" ''')
				 echo "LB: ${LB}"
				echo "application_url: ${loadbalancer}:8080/1003_CS_EmployeeReferral-0.0.1-SNAPSHOT"
				sleep 40
				}
				
			}
		}
		stage('function testing')
		{
			echo "functional testing"
			
		}
		}
		else
		{
		stage('deploy to staging')
		{
			echo "staging is not selected"
		}
		}
		
		if ( "${deploy_env}" == "prod")
		{			
        stage('deploy to prod')
		{
			container('docker')
			{
				sh ("docker build -t localhost:32121/root/docker_registry/java:${deploy_env} .")
				withCredentials([[$class: 'UsernamePasswordMultiBinding',
                credentialsId: 'gitlab',
                usernameVariable: 'DOCKER_HUB_USER',
                passwordVariable: 'DOCKER_HUB_PASSWORD']]) {
                    sh ('docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASSWORD} '+GCR_HUB_ACCOUNT )
				sh ("docker push localhost:32121/root/docker_registry/java:${deploy_env}")
			}
			}
			wrap([$class: 'BuildUser']) 
				{
			container('kubectl'){
				
				sh ("sed -i 's/tomcat-deployenv/tomcat${deploy_env}/g' tomcat.yaml")
				sh ("sed -i 's/deployenv/${deploy_env}/g' tomcat.yaml")
				sh ("sed -i 's/tomcat-deployenv/tomcat${deploy_env}/g' tomcat-svc.yaml")
              	sh ("sed -i 's/buildenv/mysql${deploy_env}db/g' mysql-pvc.yaml")
              	sh ("sed -i 's/buildenv/mysql${deploy_env}/g' mysql.yaml")
                sh("sed -i 's/builddb/mysql${deploy_env}db/g' mysql.yaml")
              	sh("kubectl apply -f namespace.yaml")
				sh '''
				committerEmail='''+ committerEmail +'''
				BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
				kubectl get secret -n ${BUILDUSER} >> nodcred.txt
				'''
			   sh '''
				if grep -q gcrcred nodcred.txt; then
				echo "gcrcred found " >> pullsecret.txt
				else
			    echo "gcrcred not found "		
				fi
				'''
                if (fileExists('pullsecret.txt'))
		  {
			   echo "gcrcred already exists"
		  }
		  else
		  {
			  echo "gcrcred does not exists"
			   withCredentials([[$class: 'UsernamePasswordMultiBinding',
                credentialsId: 'gitlab',
                usernameVariable: 'DOCKER_HUB_USER',
                passwordVariable: 'DOCKER_HUB_PASSWORD']]) {
					
					
					sh '''
					committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
					kubectl create secret docker-registry gcrcred --docker-server=localhost:32121 --docker-username=${DOCKER_HUB_USER} --docker-password=${DOCKER_HUB_PASSWORD} -n ${BUILDUSER}
					'''
				}
				
				
                  try{
                    sh '''
                    committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
                    kubectl get deployment/mysql${deploy_env} -n ${BUILDUSER}
                              '''
                    if(true)
                    {
                      echo "mysql exits"
                    }
                  }
                  catch(e)
                  {
                    sh '''
                    committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
                    kubectl apply -f mysql-pvc.yaml
                    kubectl apply -f mysql.yaml
					sleep 120
				    POD=$(kubectl get pod -l app=mysqlprod -n "${BUILDUSER}" -o jsonpath="{.items[0].metadata.name}")
					echo ${POD}
					kubectl exec -i "${POD}" -n "${BUILDUSER}" -- mysql -u root -ppassword < empreferal.sql
					'''
					
                    
                  }
				try{
				
				sh '''
				committerEmail='''+ committerEmail +'''
				BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
				kubectl get deployment/tomcat${deploy_env} -n ${BUILDUSER}
				'''
				
				if(true)
				{
					sh '''
					committerEmail='''+ committerEmail +'''
					BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
                    kubectl delete deployment tomcatdev -n ${BUILDUSER}
				    kubectl apply -f tomcat.yaml
					'''
				}
				}
				catch(e)
				{
                sh("kubectl apply -f tomcat.yaml")
				sh("kubectl apply -f tomcat-svc.yaml")
                  
				sh 'sleep 60'
				sh '''
				committerEmail='''+ committerEmail +'''
				BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`
				kubectl get svc tomcat${deploy_env} -n ${BUILDUSER}
				'''
				}
				LB = sh (returnStdout: true, script: ''' committerEmail='''+ committerEmail +''' ; BUILDUSER=`echo $committerEmail | awk -F@ '{print $1}'`;kubectl get svc tomcat${deploy_env} -n ${BUILDUSER} -o jsonpath="{ .status.loadBalancer.ingress[*]['ip', 'hostname']}" ''')
				 echo "LB: ${LB}"
				echo "application_url: ${loadbalancer}:8080/1003_CS_EmployeeReferral-0.0.1-SNAPSHOT"
				sleep 40
				}
				
			}
		}
		stage('function testing')
		{
			echo "functional testing"
			
		}
		}
		}
		else
		{
		stage('deploy to prod')
		{
			echo "prod is not selected"
		}
		}
		}
	}
		
