package lab2;

import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.

    	Signal actSignal;
    	new SignalList();
    	
    	double nextQueue = 0;
    	Random slump = new Random();
    	
    	
    	int queue1, queue2, queue3, queue4, queue5;
    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.

    	QS Q1 = new QS();
    	QS Q2 = new QS();
    	QS Q3 = new QS();
    	QS Q4 = new QS();
    	QS Q5 = new QS();


    	Gen Generator = new Gen();
    	Generator.lambda = 45; //Generator ska generera nio kunder per sekund
    	Generator.sendTo = Q1; //De genererade kunderna ska skickas till k�systemet QS

    	//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.

    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, Q1, time);
    	SignalList.SendSignal(MEASURE, Q2, time);
    	SignalList.SendSignal(MEASURE, Q3, time);
    	SignalList.SendSignal(MEASURE, Q4, time);
    	SignalList.SendSignal(MEASURE, Q5, time);

    	// Detta �r simuleringsloopen:

    	
    	//
    	
    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    		if (actSignal.signalType == 1) {
    			
    		
    		
    		
    		//i
//    		nextQueue = slump.nextDouble();
//			
//			if(nextQueue < 0.2){
//				Generator.sendTo = Q1;
//			}
//			else if(nextQueue >= 0.2 && nextQueue < 0.4){
//				Generator.sendTo = Q2;
//			}
//			else if(nextQueue >= 0.4 && nextQueue < 0.6){
//				Generator.sendTo = Q3;
//			}
//			else if(nextQueue >= 0.6 && nextQueue < 0.8){
//				Generator.sendTo = Q4;
//			}
//			else if(nextQueue >= 0.8 && nextQueue < 1.0){
//				Generator.sendTo = Q5;
//			}
    		
    		
    		//ii
//    		if(nextQueue == 0){
//				Generator.sendTo = Q1;
//				if (nextQueue < 4){
//	    			nextQueue++;
//	    		}
//	    		else{
//	    			nextQueue = 0;
//	    		}
//			}
//			else if(nextQueue == 1){
//				Generator.sendTo = Q2;
//				if (nextQueue < 4){
//	    			nextQueue++;
//	    		}
//	    		else{
//	    			nextQueue = 0;
//	    		}
//			}
//			else if(nextQueue == 2){
//				Generator.sendTo = Q3;
//				if (nextQueue < 4){
//	    			nextQueue++;
//	    		}
//	    		else{
//	    			nextQueue = 0;
//	    		}
//			}
//			else if(nextQueue == 3){
//				Generator.sendTo = Q4;
//				if (nextQueue < 4){
//	    			nextQueue++;
//	    		}
//	    		else{
//	    			nextQueue = 0;
//	    		}
//			}
//			else if(nextQueue == 4){
//				Generator.sendTo = Q5;
//				if (nextQueue < 4){
//	    			nextQueue++;
//	    		}
//	    		else{
//	    			nextQueue = 0;
//	    		}
//			}
    		}
    		
    		
    		
    		
    		//iii
    		queue1 = Q1.numberInQueue;
    		queue2 = Q2.numberInQueue;
    		queue3 = Q3.numberInQueue;
    		queue4 = Q4.numberInQueue;
    		queue5 = Q5.numberInQueue;
    		
    		if(queue1 <= queue2 && queue1 <= queue3 && queue1 <= queue4 && queue1 <= queue5){
				Generator.sendTo = Q1;
			}
			else if(queue2 <= queue1 && queue2 <= queue3 && queue2 <= queue2 && queue2 <= queue5){
				Generator.sendTo = Q2;
			}
			else if(queue3 <= queue2 && queue3 <= queue2 && queue3 <= queue4 && queue3 <= queue5){
				Generator.sendTo = Q3;
			}
			else if(queue4 <= queue2 && queue4 <= queue3 && queue4 <= queue1 && queue4 <= queue5){
				Generator.sendTo = Q4;
			}
			else if(queue5 <= queue2 && queue5 <= queue3 && queue5 <= queue4 && queue5 <= queue1){
				Generator.sendTo = Q5;
			}
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:

    	System.out.println("Medelantal kunder i k�system: " + 1.0*Q1.accumulated/Q1.noMeasurements);
    	System.out.println("Medelantal kunder i k�system: " + 1.0*Q2.accumulated/Q2.noMeasurements);
    	System.out.println("Medelantal kunder i k�system: " + 1.0*Q3.accumulated/Q3.noMeasurements);
    	System.out.println("Medelantal kunder i k�system: " + 1.0*Q4.accumulated/Q4.noMeasurements);
    	System.out.println("Medelantal kunder i k�system: " + 1.0*Q5.accumulated/Q5.noMeasurements);
    }
}
    