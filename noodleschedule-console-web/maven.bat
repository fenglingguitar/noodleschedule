call cd D:\work\source-my\noodleschedule\noodleschedule
call mvn install -P=dev -Dmaven.test.skip=true
call mvn dependency:copy-dependencies -DoutputDirectory=D:\work\source-my\noodleschedule\noodleschedule\noodleschedule-console-web\src\main\webapp\WEB-INF\lib