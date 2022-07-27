pipeline {
        agent any
        stages {
        	stage('Checkout') {
            	steps { //Checking out the repo
            		echo 'checkout repo'
                	checkout scm
            	}
        	}
        	stage('Unit & Integration Tests') {
            	steps {//run a gradle task
                	script {
                    	try {
                        		sh './gradlew clean test --no-daemon' 
                    		} finally {
                        		junit '**/build/test-results/test/*.xml' //make the junit test results available in any case (success & failure)
                    	}
                	}
            	}
            stage('Run') {
            	steps { //run application
                	script {
                    	sh './gradlew bootRun'
                	}
            	}
        	}
        }
    }