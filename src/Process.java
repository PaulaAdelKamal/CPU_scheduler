public class Process {

    String name;
    String color;
    double arrivalTime;
    double burstTime;
    double priorityNumber;
    double AGATFactor;
    double quantum;
    double waitingTime;
    double BurstTime;

    public double getReadyQTime() {
        return readyQTime;
    }

    public void setReadyQTime(double readyQTime) {
        this.readyQTime = readyQTime;
    }

    double readyQTime;
    public double getTurnaround() {
        return turnaround;
    }

    public void setTurnaround(double turnaround) {
        this.turnaround = turnaround;
    }

    double turnaround;

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Process(){
    }
    public double getRealQuantum() {
        return realQuantum;
    }

    public void setRealQuantum(double realQuantum) {
        this.realQuantum = realQuantum;
    }

    double realQuantum;

    public double getQuantum() {
        return quantum;
    }

    public void setQuantum(double quantum) {
        this.quantum = quantum;
    }

    public double getAGATFactor() {
        return AGATFactor;
    }

    public void setAGATFactor(double AGATFactor) {
        this.AGATFactor = AGATFactor;
    }

    public Process(String name,String color, double arrivalTime, double burstTime, double priorityNumber,double quantum){
        this.arrivalTime = arrivalTime;
        this.name = name;
        this.color = color;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.quantum = quantum;
        this.realQuantum = quantum;
        this.AGATFactor = -1;
        this.waitingTime = 0 ;
        this.turnaround = 0;
        this.readyQTime = this.arrivalTime;
        this.BurstTime = burstTime ;
    }
    public Process(Process pr){
        this.arrivalTime = pr.getArrivalTime();
        this.name = pr.getName();
        this.color = pr.getColor();
        this.burstTime = pr.getBurstTime();
        this.priorityNumber = pr.getPriorityNumber();
        this.quantum = pr.getQuantum();
        this.realQuantum = pr.getRealQuantum();
        this.AGATFactor = pr.getAGATFactor() ;
        this.waitingTime = 0 ;
        this.turnaround = 0;
        this.readyQTime = this.arrivalTime;
        this.BurstTime = burstTime ;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    public void setPriorityNumber(double priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public double getPriorityNumber() {
        return priorityNumber;
    }

    public String getName() {
        return name;
    }

}
