import java.util.Random;
 
class State extends GlobalSimulation{
   
    public int accumulated = 0, noMeasurements = 0;
    public int AQueue = 0, BQueue = 0;
    public int accumulatedB = 0;
   
    private EventList myEventList;
 
    Random rand = new Random();
   
    State(EventList x){
        myEventList = x;
    }
   
    private void InsertEvent(int event, double timeOfEvent, int subclass){
        myEventList.InsertEvent(event, timeOfEvent, subclass);
    }
   
   
    public void TreatEvent(Event x){
        switch (x.eventType){
            case ARRIVAL:
                if(x.subclass == A)
                    arrivalA();
                else
                    arrivalB();
                break;
            case DEPARTURE:
                if(x.subclass == A)
                    readyA();
                else
                    readyB();
                break;
            case MEASURE:
                measure();
                break;
        }
    }
   
    //beräkna medelmängden
    private double generateMean(double mean){
        return 2*mean*rand.nextDouble();
    }
   
    //Ankomst av paket
    private void arrivalA()
    {
        //System.out.println("Arrival A");
        //Om processorkön är slut så processera händelsen
        if((AQueue + BQueue) == 0)
        {
            //System.out.println("A");
            InsertEvent(DEPARTURE, time + 0.002, A);
        }
       
       
        AQueue++;
        //Lägg till framtida händelser
        InsertEvent(ARRIVAL, time - (1.0/150)*Math.log(rand.nextDouble() ), A);//generateMean(1000/150), A);
    }
    private void arrivalB()
    {
        //System.out.println("Arrival B");
        //Om processorkön är slut så processera händelsen
        if((AQueue + BQueue) == 0)
            InsertEvent(DEPARTURE, time + 0.004, B);
        BQueue++;
       
        //Lägg till framtida händelser
        //InsertEvent(ARRIVAL, time + generateMean(1000/150), A);
    }
   
    //Avkomst av paket
    private void readyA()
    {
        //System.out.println("Departure A");
        //En händelse har blivit färdigprocesserad
        //Om processorkön inte är tom så skickar man händelsen till händelselistan
        AQueue--;
        if(BQueue > 0)
            InsertEvent(DEPARTURE, time + 0.004, B);    //om det var en A händelse skicka en B händelse
        else if(AQueue > 0)
            InsertEvent(DEPARTURE, time + 0.002, A);
       
        InsertEvent(ARRIVAL, time + 1, B);
    }
    private void readyB()
    {
        //System.out.println("Departure B");
        //En händelse har blivit färdigprocesserad processera nästa
        BQueue--;
        if(BQueue > 0)
            InsertEvent(DEPARTURE, time + 0.004, B);   
        else if(AQueue > 0)
            InsertEvent(DEPARTURE, time + 0.002, A);
        //Är processorkön inte är tom så skickar man händelsen till händelselistan
        //if(numberInQueue > 0)
        //  InsertEvent(DEPARTURE, time + 1000, B); //NOTE: lite osäker på denna
    }
   
    //Mätning av paket
    private void measure(){
        //Hur många har man i processorkön
        accumulated = accumulated + AQueue + BQueue;
        accumulatedB += BQueue;
        noMeasurements++;
       
        //System.out.println(AQueue + BQueue);
       
        //System.out.println("Number of measurements: " + noMeasurements);
       
        InsertEvent(MEASURE, time + 0.1, M);
    }
}
