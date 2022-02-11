import java.util.*;
public class geneticAlgo {
    static int PopSize = 100;
    static int puzzle1PopSize = 40;
    static int binCount = 4;
    static int groupSize = 15;
    static int puzzle1GroupSize = 10;
    static ArrayList<float[]> population = new ArrayList<float[]>();
    static ArrayList<float[]> bestpopulation_puzzle1 = new ArrayList<float[]>();
    static ArrayList<float[]> pastPopulation = new ArrayList<float[]>();
    int fittest[] = {1,1,1,1,1,1,1,1,1,1};
    public static void genPop (){
        for(int i = 0; i < PopSize; i++){
            float arr[] = new float [groupSize];
            for(int j = 0; j < groupSize; j++){
                arr[j] = (int) Math.floor(Math.random() * 2);
            }
            population.add(arr);
            for(int x = 0; x < arr.length; x++){
                System.out.print(arr[x] + ",");
            }
            System.out.println();
        }
    }
    public static void genPop_puzzle1 (ArrayList<Float> numberSet){
    	float empty[] = new float [puzzle1GroupSize];
    	population.add(0, empty);
    	population.add(1, empty);
    	population.add(2, empty);
    	population.add(3, empty);
    	
    	Collections.shuffle(numberSet);
        for(int i = 0; i < binCount; i++){
        	float arr[] = new float [puzzle1GroupSize];
            for(int j = 0; j < puzzle1GroupSize; j++){
                arr[j] = numberSet.remove(0);	//add the head
            }
           
            population.set(i, arr);
            for(int x = 0; x < arr.length; x++){
                //System.out.print(arr[x] + ",");
            }
            System.out.println();
        }
    }
    public static int checkFit(ArrayList<float[]> population2) {
        int maxFit = 0;
        for (int i = 0; i < population2.size(); i++) {
            int currFit = 0;
            for (int j = 0; j < population2.get(i).length; j++) {
                if (population2.get(i)[j] == 1) {
                    currFit++;
                }
            }
            if(maxFit < currFit){
                maxFit = currFit;
            }
        }
        return maxFit;
    }
    
    public static float checkBinFitness(float[] bin, int binNumber)
    {
    	float result = -1;
    	switch(binNumber)
    	{
	    	case 1:
	    	{
	    		float product = 1;
	    		for(float e : bin)
	    		{
	    			product*=e;
	    		}
	    		result = product;
	    		break;
	    	}
	    	case 2:
	    	{
	    		float sum = 0;
	    		for(float e : bin)
	    		{
	    			sum+=e;
	    		}
	    		result = sum;
	    		break;
	    	}
	    	case 3:
	    	{
                float currMax = Integer.MIN_VALUE;
                float currMin = Integer.MAX_VALUE;
                for (int j = 0; j < puzzle1GroupSize; j++) {
                    if (bin[j] > currMax)
                    {
                        currMax = bin[j];
                    }
                	else if (bin[j] < currMin)
                        currMin = bin[j];
                }
               result = currMax - currMin;
               break;
	    	}
	    	case 4:
	    	{
	    		result = 0;
	    		break;
	    	}
    	}
    	return result;
    }
    public static int checkAllBinsFit(ArrayList<float[]> population)
    {
    	int sum = 0;
    	int binNumber = 1;
    	for (float[] bin : population)
		{
    			sum+=checkBinFitness(bin, binNumber);	
			binNumber++;
		}
    	return sum;
    }
    public static void replaceLeastFit(float[] child1, float[] child2){
        pastPopulation = population;
        int ind = getLeastFit();
        int secInd = get2ndLeastFit();
        population.set(ind, child1);
        population.set(secInd, child2);
    }
    public static void genOffSpring(float[] parent1, float[] parent2){
    	float[] child1 = parent1;
    	float[] child2 = parent2;
    	float temp = 0;
        int crossoverPoint = (int) Math.floor(Math.random() * parent1.length);
        for(int i = 0; i < crossoverPoint; i++){
            temp = child1[i];
        	child1[i] = parent2[i];
            child2[i] = temp;
        }
        replaceLeastFit(child1, child2);
    }
    public static int get2ndFittest(){
        int maxFit = 0;
        int secondFit = 0;
        int maxIndex = 0;
        int secondIndex = 0;
        for(int i = 0; i < population.size(); i++){
            int currFit = 0;
            for(int j = 0; j < population.get(i).length; j++){
                if(population.get(i)[j] == 1){
                    currFit++;
                }
            }
            if (currFit > maxFit){
                secondIndex = maxIndex;
                maxIndex = i;
                maxFit = currFit;
            }
            else if (currFit > secondFit){
                secondFit = currFit;
                secondIndex = i;
            }
        }
        return secondIndex;
    }
    public static int getFittest(){
        int maxFit = 0;
        int bestFit = 0;
        for(int i = 0; i < population.size(); i++){
            int currFit = 0;
            for(int j = 0; j < population.get(i).length; j++){
                if(population.get(i)[j] == 1){
                    currFit++;
                }
            }
            if (currFit > maxFit){
                bestFit = i;
                maxFit = currFit;
            }
        }
        return bestFit;
    }
    public static int getFittestBin(){
        float maxFit = Integer.MIN_VALUE;
        int bestFitIndex = 0;
        int binNumber = 1;
        for(float[] bin : population){
            float currFit = 0;
            currFit = checkBinFitness(bin, binNumber);
            if (currFit > maxFit){
                bestFitIndex = binNumber;
                maxFit = currFit;
            }
            binNumber++;
        }
        return bestFitIndex;
    }
    public static int get2ndFittestBin(){
        float maxFitValue = Integer.MIN_VALUE;
        int bestFitIndex = 0;
        int second_bestFitIndex = 0;
        float second_maxFitValue = Integer.MIN_VALUE;
        int binNumber = 1;
        
        for(float[] bin : population){
            float currFit = 0;
            currFit = checkBinFitness(bin, binNumber);
            if (currFit > maxFitValue){
            	second_bestFitIndex = bestFitIndex;
                bestFitIndex = binNumber;
                maxFitValue = currFit;
            }
            if (currFit > second_maxFitValue){
            	second_maxFitValue = currFit;
                second_bestFitIndex = binNumber;
            }
            binNumber++;
        }
        return second_bestFitIndex;
    }
    public static int getLeastFit(){
        int minFit = 0;
        int bestFit = 0;
        for(int i = 0; i < population.size(); i++){
            int currFit = 0;
            for(int j = 0; j < population.get(i).length; j++){
                if(population.get(i)[j] == 0){
                    currFit++;
                }
            }
            if (currFit  > minFit){
                bestFit = i;
                minFit = currFit;
            }
        }
        return bestFit;
    }
    public static void mutate(){
        int mutationPoint = (int) Math.floor(Math.random() * groupSize);
        if(population.get(getFittest())[mutationPoint] == 1){
            population.get(getFittest())[mutationPoint] = 0;
        }
        else{
            population.get(getFittest())[mutationPoint] = 1;
        }
        mutationPoint = (int) Math.floor(Math.random() * groupSize);
        if(population.get(get2ndFittest())[mutationPoint] == 1){
            population.get(get2ndFittest())[mutationPoint] = 0;
        }
        else{
            population.get(get2ndFittest())[mutationPoint] = 1;
        }
    }
    
    public static void mutate_puzzle1()
    {
    	int mutationPoint = (int) Math.floor(Math.random() * puzzle1GroupSize);
    	int mutationPoint2 = (int) Math.floor(Math.random() * puzzle1GroupSize);
    	
    	float swapper1;
    	float swapper2;
        swapper1 = population.get(getFittest())[mutationPoint];
        swapper2 = population.get(get2ndFittest())[mutationPoint2];
        population.get(getFittest())[mutationPoint] = swapper2;
        population.get(get2ndFittest())[mutationPoint2] = swapper1;
        
    }
    public static int get2ndLeastFit(){
        int minFit = 0;
        int secondMinFit = 0;
        int secondMinIndex = 0;
        int minIndex = 0;
        for(int i = 0; i < population.size(); i++){
            int currFit = 0;
            for(int j = 0; j < population.get(i).length; j++){
                if(population.get(i)[j] == 0){
                    currFit++;
                }
            }
            if (currFit  > minFit){
                secondMinIndex = minIndex;
                minIndex = i;
                minFit = currFit;
            }
            else if (currFit > secondMinFit){
                secondMinFit = currFit;
                secondMinIndex = i;

            }
        }
        return secondMinIndex;
    }
    
    public static void main(String[] args) {
    	int mode = Integer.parseInt(args[0]);
    	String filename = args[1];
    	switch(mode)
    	{
	    	case 0:
	    	{
	    		Random rand = new Random();
	            genPop();
	            int genCount = 0;
	            while (checkFit(population) < 15) {			//was 15
	                System.out.println("Generation: " + genCount + " Fitness: " + checkFit(population));
	                if(rand.nextInt()%9 < 6){
	                    mutate();
	                }
	                float[] parent1 = population.get(getFittestBin());
	                float[] parent2 = population.get(get2ndFittestBin());
	                genOffSpring(parent1, parent2);
	                genCount++;
	            }
	            System.out.println("Solution found at in Generation: " + genCount + " at fitness: " + checkFit(population));
	            break;
	    	}
	    	
	    	case 1:
	    	{
	    		puzzle1();
	    		Random rand = new Random();
	    		ArrayList<Float> numberSet = ReadFile.read(filename);
	    		genPop_puzzle1(numberSet);
	    		//System.out.println(population.get(1));
	    		//System.out.println(population.get(2));
	    		printout();
	    		
	    		int genCount = 0;
	    		int oneMillion = 1000000;
	            while (checkAllBinsFit(population) < oneMillion*3 && genCount < 400) {			//was 15
	                System.out.println("Generation: " + genCount + " Fitness: " + checkAllBinsFit(population));
	                if(rand.nextInt()%9 > 6){
	                	mutate_puzzle1();
	                	System.out.println("* Mutation *");
	                }
	                float[] parent1 = population.get(getFittestBin());
	                float[] parent2 = population.get(get2ndFittestBin());
	                genOffSpring(parent1, parent2);
	                genCount++;
	            }
	            System.out.println("Solution found at in Generation: " + genCount + " at fitness: " + checkAllBinsFit(population));
	            printout_withfit();
	            break;
	    	}
    	}
    }
    
    public static void printout()
    {
    	for (float[] bin : population)
		{
			for (float e : bin)
    		{
    			System.out.print(e + "\t");
    		}
			System.out.print("\n");
		}
    }
    public static void printout_withfit()
    {
    	int binNumber = 1;
    	for (float[] bin : population)
		{
			for (float e : bin)
    		{
    			System.out.print(e + "\t");
    		}
			System.out.print("Bin Fitness: " + checkBinFitness(bin, binNumber));
			System.out.print("\n");
			binNumber++;
		}
    }
    public static void puzzle1()
    {
    	System.out.println("this is puzzle 1.");
    }
}
