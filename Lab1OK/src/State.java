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
   
    //ber�kna medelm�ngden
    private double generateMean(double mean){
        return 2*mean*rand.nextDouble();
    }
   
    //Ankomst av paket
    private void arrivalA()
    {
        //System.out.println("Arrival A");
        //Om processork�n �r slut s� processera h�ndelsen
        if((AQueue + BQueue) == 0)
        {
            //System.out.println("A");
            InsertEvent(DEPARTURE, time + 0.002, A);
        }
       
       
        AQueue++;
        //L�gg till framtida h�ndelser
        InsertEvent(ARRIVAL, time - (1.0/150)*Math.log(rand.nextDouble() ), A);//generateMean(1000/150), A);
    }
    private void arrivalB()
    {
        //System.out.println("Arrival B");
        //Om processork�n �r slut s� processera h�ndelsen
        if((AQueue + BQueue) == 0)
            InsertEvent(DEPARTURE, time + 0.004, B);
        BQueue++;
       
        //L�gg till framtida h�ndelser
        //InsertEvent(ARRIVAL, time + generateMean(1000/150), A);
    }
   
    //Avkomst av paket
    private void readyA()
    {
        //System.out.println("Departure A");
        //En h�ndelse har blivit f�rdigprocesserad
        //Om processork�n inte �r tom s� skickar man h�ndelsen till h�ndelselistan
        AQueue--;
        if(BQueue > 0)
            InsertEvent(DEPARTURE, time + 0.004, B);    //om det var en A h�ndelse skicka en B h�ndelse
        else if(AQueue > 0)
            InsertEvent(DEPARTURE, time + 0.002, A);
       
        InsertEvent(ARRIVAL, time + 1, B);
    }
    private void readyB()
    {
        //System.out.println("Departure B");
        //En h�ndelse har blivit f�rdigprocesserad processera n�sta
        BQueue--;
        if(BQueue > 0)
            InsertEvent(DEPARTURE, time + 0.004, B);   
        else if(AQueue > 0)
            InsertEvent(DEPARTURE, time + 0.002, A);
        //�r processork�n inte �r tom s� skickar man h�ndelsen till h�ndelselistan
        //if(numberInQueue > 0)
        //  InsertEvent(DEPARTURE, time + 1000, B); //NOTE: lite os�ker p� denna
    }
   
    //M�tning av paket
    private void measure(){
        //Hur m�nga har man i processork�n
        accumulated = accumulated + AQueue + BQueue;
        accumulatedB += BQueue;
        noMeasurements++;
       
        //System.out.println(AQueue + BQueue);
       
        //System.out.println("Number of measurements: " + noMeasurements);
       
        InsertEvent(MEASURE, time + 0.1, M);
    }
}
