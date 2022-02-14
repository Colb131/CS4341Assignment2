import java.util.*;
public class geneticAlgo {
    static int PopSize = 10;
    static int puzzle1PopSize = 40;
    static int binCount = 4;
    static int puzzle1GroupSize = 10;
    static ArrayList<ArrayList<float[]>> population = new ArrayList<>();
    static ArrayList<float[]> bestpopulation_puzzle1 = new ArrayList<>();
    static String filename;
    static double bestScore = 0;
    static int bestGen = 0;
    public static void genPop_puzzle1 (){
        ArrayList<Float> numberSet = ReadFile.read(filename);
        ArrayList<float[]> currSet = new ArrayList<>();
    	float empty[] = new float [puzzle1GroupSize];
    	currSet.add(0, empty);
        currSet.add(1, empty);
        currSet.add(2, empty);
        currSet.add(3, empty);

    	Collections.shuffle(numberSet);
        for(int i = 0; i < binCount; i++){
        	float arr[] = new float [puzzle1GroupSize];
            for(int j = 0; j < puzzle1GroupSize; j++){
                arr[j] = numberSet.remove(0);	//add the head
            }
            currSet.set(i, arr);
        }
        population.add(currSet);
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
    public static float checkAllBinsFit(ArrayList<float[]> population)
    {
    	float sum = 0;
    	int binNumber = 1;
    	for (float[] bin : population)
		{
    			sum+=checkBinFitness(bin, binNumber);	
			binNumber++;
		}
    	return sum;
    }

    public static void replaceMostFit(ArrayList<float[]> child1, ArrayList<float[]> child2){
        Random rand = new Random();
        int ind = getFittestP1();
        int secInd =  get2ndFittestP1();
        while(secInd == ind){
            secInd =  rand.nextInt(PopSize);
        }
        population.set(ind, child1);
        population.set(secInd, child2);
    }
    public static ArrayList<float[]> fixErrors (ArrayList<float[]> list){
        ArrayList<Float> numberSet = ReadFile.read(filename);
        ArrayList<int[]> repeatNum = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.get(i).length; j++){
                boolean numFound = false;
                for(int z = 0; z < numberSet.size(); z++){

                    if(list.get(i)[j] == numberSet.get(z)){
                        numberSet.remove(z);
                        z--;
                        numFound = true;
                        break;
                    }
                }
                if(!numFound){
                    int arr[] = {i, j};
                    repeatNum.add(arr);
                }
            }
        }
        if(!repeatNum.isEmpty()) {
            for (int i = 0; i < repeatNum.size(); i++) {
                list.get(repeatNum.get(i)[0])[repeatNum.get(i)[1]] = numberSet.remove(0);
            }
        }
        return list;
    }
    public static void genOffSpring(ArrayList<float[]> parent1, ArrayList<float[]> parent2){
    	ArrayList<float[]> child1 = parent1;
    	ArrayList<float[]> child2 = parent2;
    	float temp = 0;
        int crossoverPoint = (int) Math.floor(Math.random() * puzzle1GroupSize);
        for(int b = 0; b < parent1.size(); b++) {
            for (int i = 0; i < crossoverPoint; i++) {
                temp = child1.get(b)[i];
                child1.get(b)[i] = parent2.get(b)[i];
                child2.get(b)[i] = temp;
            }
        }
        child1 = fixErrors(child1);
        child2 = fixErrors(child2);
        replaceMostFit(child1, child2);
    }
    public static int getFittestP1(){
        float maxFitValue = Integer.MIN_VALUE;
        int bestFitIndex = 0;
        for(int i = 0; i < population.size(); i++){
            float currFit = 0;
            currFit = checkAllBinsFit(population.get(i));
            if (currFit > maxFitValue){
                bestFitIndex = i;
                maxFitValue = currFit;
            }
        }
        return bestFitIndex;
    }
    public static int get2ndFittestP1(){
        float maxFitValue = Integer.MIN_VALUE;
        int bestFitIndex = 0;
        int second_bestFitIndex = 0;
        float second_maxFitValue = Integer.MIN_VALUE;
        for(int i = 0; i < population.size(); i++){
            float currFit = 0;
            currFit = checkAllBinsFit(population.get(i));
            if (currFit > maxFitValue){
                second_bestFitIndex = bestFitIndex;
                bestFitIndex = i;
                maxFitValue = currFit;
            }
            else if (currFit > second_maxFitValue){
                second_maxFitValue = currFit;
                second_bestFitIndex = i;
            }
        }
        return second_bestFitIndex;
    }
    public static int get2ndLeastFitP1(){
        float minFitValue = Integer.MAX_VALUE;
        int bestFitIndex = 0;
        int second_bestFitIndex = 0;
        float second_minFitValue = Integer.MAX_VALUE;
        for(int i = 0; i < population.size(); i++){
            float currFit = 0;
            currFit = checkAllBinsFit(population.get(i));
            if (currFit < minFitValue){
                second_bestFitIndex = bestFitIndex;
                bestFitIndex = i;
                minFitValue = currFit;
            }
            else if (currFit < second_minFitValue){
                second_minFitValue = currFit;
                second_bestFitIndex = i;
            }
        }
        return second_bestFitIndex;
    }
    public static int getLeastFitP1(){
        float minFitValue = Integer.MAX_VALUE;
        int bestFitIndex = 0;

        for(int i = 0; i < population.size(); i++){
            float currFit = 0;
            currFit = checkAllBinsFit(population.get(i));
            if (currFit < minFitValue){
                bestFitIndex = i;
                minFitValue = currFit;
            }
        }
        return bestFitIndex;
    }
    public static void mutate_puzzle1()
    {
        Random rand = new Random();
    	float swapper1;
    	float swapper2;
        int mutated = rand.nextInt(PopSize);
        int mutationCount = rand.nextInt(5);
        for (int i = 0; i < mutationCount; i++){
            int bin1 = rand.nextInt(4);
            int bin2 = rand.nextInt(4);
            while(bin2 == bin1){
                bin2 = rand.nextInt(4);
            }
            int mutationPoint = rand.nextInt(puzzle1GroupSize);
            int mutationPoint2 = rand.nextInt(puzzle1GroupSize);
            swapper1 = population.get(mutated).get(bin1)[mutationPoint];
            swapper2 = population.get(mutated).get(bin2)[mutationPoint2];
            population.get(mutated).get(bin1)[mutationPoint] = swapper2;
            population.get(mutated).get(bin2)[mutationPoint2] = swapper1;
        }
    }
    public static void main(String[] args) {
        filename = args[1];
    	int mode = Integer.parseInt(args[0]);
    	switch(mode) {

            case 1: {
                puzzle1();
                Random rand = new Random();
                for (int i = 0; i < PopSize; i++) {
                    genPop_puzzle1();
                }
                int genCount = 0;
                float startTime = System.nanoTime();
                float checkTime = (Float.parseFloat(args[2])) * 1000000000;
                float endTime = 0;
                while (endTime - startTime < checkTime) {
                    System.out.println("Generation: " + genCount + " Fitness " + checkAllBinsFit(population.get(getFittestP1())));
//                    if (genCount%100 == 1){
//                        System.out.println(checkAllBinsFit(population.get(getFittestP1())));
//                    }
                    if (bestScore < checkAllBinsFit(population.get(getFittestP1()))) {
                        bestScore = checkAllBinsFit(population.get(getFittestP1()));
                        bestGen = genCount;
                        Iterator<float[]> iterator = population.get(getFittestP1()).iterator();
                        bestpopulation_puzzle1 = new ArrayList<>();
                        while (iterator.hasNext()) {
                            bestpopulation_puzzle1.add(iterator.next().clone());
                        }
                    }
                    if (rand.nextInt() % 9 > 6) {
                        mutate_puzzle1();
                        System.out.println("* Mutation *");
                    }
                    ArrayList<float[]> parent1 = population.get(getFittestP1());
                    ArrayList<float[]> parent2 = population.get(get2ndFittestP1());
                    genOffSpring(parent1, parent2);
                    genCount++;
                    endTime = System.nanoTime();
                }
                System.out.println("Ran for: " + args[2] + " seconds");
                System.out.println("Solution found at in Generation: " + bestGen + " at fitness: " + bestScore);
                printout_oneBin();
                break;
            }
        }
    }



    public static void printout()
    {
        for(int i = 0; i < PopSize; i++)
		{
            System.out.println(i);
            for (float[] bin : population.get(i))
            {
                for (float e : bin)
                {
                    System.out.print(e + "\t");
                }
                System.out.print("\n");
            }
        }
    }
    public static void printout_oneBin(){
        int binNumber = 1;
        for (float[] bin : bestpopulation_puzzle1)
        {
            for (float e : bin)
            {
                System.out.print(e + "\t");
            }
            System.out.print("Bin: " + binNumber + " Fitness: " + checkBinFitness(bin, binNumber));
            System.out.print("\n");
            binNumber++;
        }
    }
    public static void printout_withfit()
    {
        for(int i = 0; i < PopSize; i++)
        {
            System.out.println(i);
            int binNumber = 1;
            for (float[] bin : population.get(i))
            {
                for (float e : bin)
                {
                    System.out.print(e + "\t");
                }
                System.out.print("Bin: " + binNumber + " Fitness: " + checkBinFitness(bin, binNumber));
                System.out.print("\n");
                binNumber++;
            }
        }
    }
    public static void puzzle1()
    {
    	System.out.println("this is puzzle 1.");
    }
}
