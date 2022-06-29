import java.util.*;
import java.lang.Math;

public class CPUscheduler{
    ArrayList<Process> processes;
    int contextSwitch;
    int processNum;
    ArrayList<Double> agatWaitingTimes ;
    ArrayList<Process> AGATResults ;
    public CPUscheduler(int contextSwitch, int processNum) {
        this.processes = new ArrayList<Process>();
        this.processNum = processNum;
        this.contextSwitch = contextSwitch;
    }


    public void add(Process p){
        processes.add(p);
    }

    public void shortestRemainingTimeFirst(){
        ArrayList<Process> Ps =  new ArrayList<Process>();
        for (int nedo = 0 ; nedo < processNum ; nedo++)
        {
            Ps.add(new Process(processes.get(nedo)));
        }
        ArrayList<Process> ready = new ArrayList<Process>();
        ArrayList<Double> waiting = new ArrayList<>();
        ArrayList<Double> turnround = new ArrayList<>();
        ArrayList<String> pnames = new ArrayList<>();
        double time = 0;
        int j = 0;
        Process execute = Ps.get(1);
        int num=0;
        int x=0;
        while(processNum - turnround.size() != 0) {
            int i ;

            // adding the process to the ready queue
            if (j < processNum) { //condition to avoid selecting out of range element
                while (Ps.get(j).arrivalTime <= time) {
                    ready.add(Ps.get(j));
                    j += 1;
                    if (j == processNum) break; //condition to avoid selecting out of range element
                }
            }
            if (!ready.isEmpty()) {
                double min = 1000;
                // searching for the shortest burst time process
                for (i = 0; i < ready.size(); i++) {
                    if (ready.get(i).burstTime < min){
                        min = ready.get(i).burstTime;
                        num = i;
                    }
                }
            }
            if(ready.get(num).getName().equals(execute.getName())){
                time+=1;
                execute.burstTime-=1;
            }else{
                time+=contextSwitch;
                execute = ready.get(num);
                time+=1;
                execute.burstTime-=1;
            }
            if(execute.burstTime == 0){
                double TurnRound = time-execute.arrivalTime;
                waiting.add(TurnRound - execute.BurstTime);
                turnround.add(TurnRound);
                pnames.add(execute.getName());
                time+=contextSwitch;
                for ( i= 0; i<ready.size();i++){
                    if(ready.get(i).getName().equals(execute.getName())){
                        ready.remove(i);
                    }
                }
                x+=1;
                if(x%3 == 0 && !ready.isEmpty()){
                    for(i = 0; i<ready.size(); i++){
                        waiting.add(time - ready.get(i).arrivalTime);
                        turnround.add((time - ready.get(i).arrivalTime)+ ready.get(i).burstTime);
                        pnames.add(ready.get(i).getName());
                        time+=ready.get(i).burstTime;
                        ready.remove(i);

                    }
                }
            }


        }
        System.out.println("Shortest remaining time first");
        System.out.println(pnames);
        System.out.println("waiting time");
        System.out.println(waiting);
        System.out.println("average waiting time");
        double sum = 0;
        for(Double d : waiting) sum += d;
        System.out.println(sum/waiting.size());
        System.out.println("turn round time");
        System.out.println(turnround);
        System.out.println("average turn round time");
        sum = 0;
        for(Double d : turnround) sum += d;
        System.out.println(sum/turnround.size());
    }

    public void shortestJobFirst(){
        //define wanted variables
        ArrayList<Process> Ps = processes;
        ArrayList<Process> ready = new ArrayList<Process>();
        ArrayList<Double> waiting = new ArrayList<>();
        ArrayList<Double> turnround = new ArrayList<>();
        ArrayList<String> pnames = new ArrayList<>();
        double time = 0;
        int j = 0;
        int x = 0;
        // working till all process are finished
        while(processNum - waiting.size() != 0){
            // adding the process to the ready queue
            if(j < processNum){ //condition to avoid selecting out of range element
                while(Ps.get(j).arrivalTime<=time ){
                    ready.add(Ps.get(j));
                    j+=1;
                    if(j==processNum)break; //condition to avoid selecting out of range element
                }
            }
            // condition to check if the ready queue have elements to run
            if(!ready.isEmpty()){
                double min = 1000;
                Process excecute = new Process();
                // searching for the shortest burst time process
                for (int i= 0; i<ready.size();i++ ){
                    if(ready.get(i).burstTime<min){
                        min = ready.get(i).burstTime;
                        excecute = ready.get(i);
                    }
                }
                // calculating the waiting time and turn round time
                double waitingTime = time - excecute.getArrivalTime();
                waiting.add(waitingTime);
                time+=excecute.burstTime ;
                turnround.add(waitingTime+ excecute.getBurstTime());
                pnames.add(excecute.getName());
                x+=1;
                //remove the process from ready queue
                for (int i= 0; i<ready.size();i++ ){
                    if(ready.get(i).getName().equals(excecute.getName())){
                        ready.remove(i);
                    }
                }
                //handling the starvation problem
                // by emptying the ready queue after executing 3 shortest process
                if(!ready.isEmpty() && x%3 == 0){
                    for (int i= 0; i<ready.size();i++ ){
                        excecute = ready.get(i);
                        waitingTime = time - excecute.getArrivalTime();
                        waiting.add(waitingTime);
                        time+=excecute.burstTime;
                        turnround.add(waitingTime+ excecute.getBurstTime());
                        pnames.add(excecute.getName());
                        ready.remove(i);
                    }
                }
            }else {
                time+=1;
            }
        }
        //printing the findings
        System.out.println("Shortest job first");
        System.out.println(pnames);
        System.out.println("waiting time");
        System.out.println(waiting);
        System.out.println("average waiting time");
        double sum = 0;
        for(Double d : waiting) sum += d;
        System.out.println(sum/waiting.size());
        System.out.println("turn round time");
        System.out.println(turnround);
        System.out.println("average turn round time");
        sum = 0;
        for(Double d : turnround) sum += d;
        System.out.println(sum/turnround.size());
    }
    public void priorityScheduling(){
        ArrayList<Process> Ps = processes;
        ArrayList<Process> ready = new ArrayList<Process>();
        ArrayList<Double> waiting = new ArrayList<>();
        ArrayList<Double> turnround = new ArrayList<>();
        ArrayList<String> pnames = new ArrayList<>();
        double time = 0;
        int j = 0;
        while(processNum - waiting.size() != 0){
            if(j < processNum){
                while(Ps.get(j).arrivalTime<=time ){
                    ready.add(Ps.get(j));
                    j+=1;
                    if(j==processNum)break;
                }
            }
            if(!ready.isEmpty()){
                double min = 1000;
                Process excecute = new Process();
                for (int i= 0; i<ready.size();i++ ){
                    if(ready.get(i).priorityNumber<min){
                        min = ready.get(i).priorityNumber;
                        excecute = ready.get(i);
                    }
                }
                double waitingTime = time - excecute.getArrivalTime();
                waiting.add(waitingTime);
                time+=excecute.burstTime + contextSwitch;
                turnround.add(waitingTime+ excecute.getBurstTime()+contextSwitch);
                pnames.add(excecute.getName());
                if(!ready.isEmpty()){
                    for (int i= 0; i<ready.size();i++ ){
                        ready.get(i).priorityNumber-=1;
                    }
                }
                for (int i= 0; i<ready.size();i++ ){
                    if(ready.get(i).getName().equals(excecute.getName())){
                        ready.remove(i);
                    }
                }
            }else {
                time+=1;
            }
        }
        System.out.println("priority scheduling");
        System.out.println(pnames);
        System.out.println("waiting time");
        System.out.println(waiting);
        System.out.println("average waiting time");
        double sum = 0;
        for(Double d : waiting) sum += d;
        System.out.println(sum/waiting.size());
        System.out.println("turn round time");
        System.out.println(turnround);
        System.out.println("average turn round time");
        sum = 0;
        for(Double d : turnround) sum += d;
        System.out.println(sum/turnround.size());
    }
    public double getV1(ArrayList<Process> processes){
        double maxArrivalTime = -1;
        for (Process pr : processes){
            if (pr.getBurstTime() == 0) continue;
            if (pr.getArrivalTime() > maxArrivalTime){
                maxArrivalTime = pr.getArrivalTime();
            }
        }
        if (maxArrivalTime > 10) {
            maxArrivalTime = maxArrivalTime / (double) (10);
            return maxArrivalTime;
        }else {
            return 1;
        }
    }
    public double getV2(int i){
        double maxRemainingBurstTime = -1;
        for (Process pr : processes){
            if (pr.getBurstTime() == 0) continue;
            if (pr.getArrivalTime() > i) continue;
            if (pr.getBurstTime() > maxRemainingBurstTime){
                maxRemainingBurstTime =  (pr.getBurstTime());
            }
        }
        if (maxRemainingBurstTime > 10) {
            maxRemainingBurstTime = maxRemainingBurstTime / (double)(10);
            return maxRemainingBurstTime;
        }else {
            return 1;
        }
    }

    public ArrayList<Process> Agat(){
        AGATResults = new ArrayList<Process>();
        agatWaitingTimes = new ArrayList<Double>();
        int sumSecs = 0;
        for (Process pr: processes){
            sumSecs += pr.getBurstTime();
        }
        double minArrivalTime = 99999999;
        int minArrivalTimeIndex = 0;
        for (int i=0;i<processes.size();i++ ){
            if (processes.get(i).getArrivalTime() < minArrivalTime){
                minArrivalTimeIndex = i;
                minArrivalTime = processes.get(i).getArrivalTime();
            }
        }
//        ArrayList<P>
        ArrayList<Process> result = new ArrayList<Process>();
        ArrayList<Integer> resultSec  = new ArrayList<Integer>( );
        ArrayList<Integer> readyQ = new ArrayList<Integer>();
        ArrayList<Process> deadList = new ArrayList<Process>();

        int runningProcessIndex = minArrivalTimeIndex;
        int runningProcessCurrentTime =  0;
        int i= 0;
//        AGATResults.add(new Process(processes.get(runningProcessIndex)));
//        resultSec.add(i);
        double v1 =getV1(processes);
//        double v2 = getV2(i);
        int realSec = 0;
        do {

//            if (realSec != 0){
            i += 1;
            runningProcessCurrentTime += 1;
//            }
//            realSec+=1;
            processes.get(runningProcessIndex).setBurstTime(processes.get(runningProcessIndex).getBurstTime() -1);
//            System.out.println("<<<<<<<<<<<< "+ i +" >>>>>>>>>>>>>>>>>>");
//            for (Process pr : processes){
//                if (pr.getBurstTime()== 0) continue;
//                System.out.println(pr.getAGATFactor() + " " + pr.getName()  + " "  + pr.getBurstTime() + " " + Math.ceil((double)(pr.getBurstTime()) / getV2(i)) + " <<<<<<<<<<<<<<<<<<<<<<<");
//            }
//            System.out.println("<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");
            if (processes.get(runningProcessIndex).getBurstTime()  == 0){
                deadList.add(processes.get(runningProcessIndex));
                processes.get(runningProcessIndex).setQuantum(0);
                processes.get(runningProcessIndex).setRealQuantum(0);
                processes.get(runningProcessIndex).setTurnaround(i-processes.get(runningProcessIndex).getArrivalTime());

//                processes.get(runningProcessIndex).setBurstTime(0);
                AGATResults.add(new Process(processes.get(runningProcessIndex)));
                resultSec.add(i);

//                processes.remove(runningProcessIndex);
                if (!readyQ.isEmpty()) {


                    runningProcessIndex = readyQ.get(0);
                    readyQ.remove(0);
                    runningProcessCurrentTime = 0;
                }
                continue;
            }
            for (int x=0;x< processes.size();x++){
                if (processes.get(x).getArrivalTime() == i) {
                    readyQ.add(x);
                    processes.get(x).setReadyQTime(processes.get(x).getArrivalTime());
                }
            }
            for (int x =0; x<readyQ.size();x++){
                if (processes.get(readyQ.get(x)).getBurstTime() == 0){
                    readyQ.remove(x);
                }
            }

            if (runningProcessCurrentTime >= processes.get(runningProcessIndex).getQuantum()){
                readyQ.add(runningProcessIndex);
                processes.get(runningProcessIndex).setReadyQTime(i);
                AGATResults.add(new Process(processes.get(runningProcessIndex)));
                resultSec.add(i);
                processes.get(runningProcessIndex).setQuantum(processes.get(runningProcessIndex).getQuantum() + 2 );
//                processes.get(runningProcessIndex).setBurstTime(processes.get(runningProcessIndex).getBurstTime() - runningProcessCurrentTime );


                runningProcessIndex = readyQ.get(0);
                agatWaitingTimes.add(i - processes.get(runningProcessIndex).getReadyQTime());
                processes.get(runningProcessIndex).setWaitingTime(i - processes.get(runningProcessIndex).getReadyQTime() +processes.get(runningProcessIndex).getWaitingTime());
                readyQ.remove(0);
                runningProcessCurrentTime =0;
                continue;
            }
//            if (i == 14) {

//            }
            if (runningProcessCurrentTime >=  Math.round((double) (processes.get(runningProcessIndex).getQuantum()) * 0.4)){

                double v2 = getV2(i);
                int minAGATIndex = runningProcessIndex;
                for (int j =0;j< processes.size();j++){
                    if (processes.get(j).getArrivalTime() > i || processes.get(j).getBurstTime() == 0 )  {
                        continue;
                    }
                    processes.get(j).setAGATFactor((int) (10 -processes.get(j).getPriorityNumber() +  Math.ceil(processes.get(j).getArrivalTime()/ v1) + Math.ceil(processes.get(j).getBurstTime()/v2)));
                    if (processes.get(j).getAGATFactor() < processes.get(minAGATIndex).getAGATFactor()){
                        minAGATIndex = j;
                    }

                }

                if (minAGATIndex != runningProcessIndex){
                    double currQ = processes.get(runningProcessIndex).getRealQuantum();
                    readyQ.add(runningProcessIndex);
                    processes.get(runningProcessIndex).setReadyQTime(i);
                    processes.get(runningProcessIndex).setQuantum(processes.get(runningProcessIndex).getQuantum()+ (currQ-runningProcessCurrentTime));
//                    processes.get(runningProcessIndex).setBurstTime(processes.get(runningProcessIndex).getBurstTime() - runningProcessCurrentTime);
                    AGATResults.add(new Process(processes.get(runningProcessIndex)));
                    resultSec.add(i);
                    runningProcessCurrentTime =0;
                    runningProcessIndex = minAGATIndex;
                    agatWaitingTimes.add(i - processes.get(runningProcessIndex).getReadyQTime());
                    for (int s=0;s<readyQ.size();s++){
                        if (readyQ.get(s) == minAGATIndex){
                            readyQ.remove(s);
                            break;
                        }
                    }
                    continue;
                }else{
                    AGATResults.add(new Process(processes.get(runningProcessIndex)));
                    resultSec.add(i);
                    continue;
                }

            }
            AGATResults.add(new Process(processes.get(runningProcessIndex)));
            resultSec.add(i);

        }while (i< sumSecs);

        for(int last=0;last<AGATResults.size();last++){
            System.out.println(resultSec.get(last) +" "+ AGATResults.get(last).getName());
        }
        return AGATResults;
    }
    public  double getAGATAvgWaitingTime(){
        double avgWaitingAgatTime = 0;
        for (Double d: agatWaitingTimes){
            avgWaitingAgatTime += d/ (double) agatWaitingTimes.size();
        }
        return avgWaitingAgatTime;
    }
    public double getAvgAGATTurnaroundTime(){
        double avgTurnaroundTime = 0;
        for(Process pr : processes){
            avgTurnaroundTime += pr.getTurnaround() / processes.size();
        }
        return avgTurnaroundTime;
    }
    public Map<String,LinkedHashSet<Double>> getAGATQuantumTimeHistory(){
        Map<String,LinkedHashSet<Double>> myMap= new HashMap<String,LinkedHashSet<Double>>();
        for(int i=0;i<AGATResults.size();i++){
            if (myMap.containsKey(AGATResults.get(i).getName())){
                myMap.get(AGATResults.get(i).getName()).add(AGATResults.get(i).getQuantum());
            }else{
                myMap.put(AGATResults.get(i).getName(),new LinkedHashSet<Double>());
                myMap.get(AGATResults.get(i).getName()).add(AGATResults.get(i).getQuantum());
            }
        }
        return myMap;
    }

    public Map<String,LinkedHashSet<Double>> getAgetAGATFactorHistory(){
        Map<String,LinkedHashSet<Double>> myMap= new HashMap<String,LinkedHashSet<Double>>();
        for(int i=0;i<AGATResults.size();i++){
            if (myMap.containsKey(AGATResults.get(i).getName())){
                myMap.get(AGATResults.get(i).getName()).add(AGATResults.get(i).getAGATFactor());
            }else{
                myMap.put(AGATResults.get(i).getName(),new LinkedHashSet<Double>());
                myMap.get(AGATResults.get(i).getName()).add(AGATResults.get(i).getAGATFactor());
            }
        }
        return myMap;
    }
    public static  void main(String[] args){

        CPUscheduler cpu = new CPUscheduler(1,4);
        Process p1 = new Process("p1","red" , 0 , 17 , 4 , 4);
        Process p2 = new Process("p2","yellow" , 3 , 6 , 9 , 3);
        Process p3 = new Process("p3","black" , 4 , 10 , 3 , 5);
        Process p4 = new Process("p4","blue" , 29 , 4 ,10  , 2);
        cpu.add(p1);
        cpu.add(p2);
        cpu.add(p3);
        cpu.add(p4);
        cpu.priorityScheduling();
        System.out.println(" ");
        cpu.shortestJobFirst();
        System.out.println(" ");
        cpu.shortestRemainingTimeFirst();
        System.out.println(" ");

        ArrayList<Process> results = cpu.Agat();
        Map<String,LinkedHashSet<Double>> AGATQuantumTimes = cpu.getAGATQuantumTimeHistory();
        for (String key : AGATQuantumTimes.keySet()){
            System.out.println(key +  AGATQuantumTimes.get(key));
        }
        //cpu.shortestRemainingTimeFirst();
//        for(int i=0;i<results.size();i++){
//            System.out.println(i +" "+ results.get(i).getName());
//        }
    }

}
