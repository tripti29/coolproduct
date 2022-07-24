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
            	steps {
                	script {
                    	try {
                        		sh './gradlew clean test --no-daemon' //run a gradle task
                    		} finally {
                        		junit '**/build/test-results/test/*.xml' //make the junit test results available in any case (success & failure)
                    	}
                	}
            	}
        	}
            stage('Test') {
                steps {
                    echo 'Hello World ...'
                }
            }
        }
    }