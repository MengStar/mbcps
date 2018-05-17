执行压力测试：

    cd gatling/gatling-scripts
    mvn gatling:test -Dgatling.simulationClass=test.load.sims.LoadSimulation -Dbase.url=http://localhost:8401/ -Dtest.path=/role/ -Dsim.users=3000
    mvn gatling:test -Dgatling.simulationClass=test.load.sims.LoadSimulation -Dbase.url=http://localhost:8402/ -Dtest.path="quotes-reactive-paged?page=1&size=10" -Dsim.users=10

