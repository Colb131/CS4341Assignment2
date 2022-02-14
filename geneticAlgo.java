import java.util.*;
public class geneticAlgo {
    static int PopSize = 10;
    static int puzzle1PopSize = 40;
    static int binCount = 4;
    static int groupSize = 15;
    static int puzzle1GroupSize = 10;
    static ArrayList<ArrayList<float[]>> population = new ArrayList<>();
    static ArrayList<float[]> bestpopulation_puzzle1 = new ArrayList<>();
    
    static ArrayList<Tower> population_puzzle2 = new ArrayList<>();
    static Tower bestTower;
    
    static String filename;
    static double bestScore = -1;
    static int bestGen = 0;
    int fittest[] = {1,1,1,1,1,1,1,1,1,1};
    /*
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
    } */
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
    public static void genPop_puzzle2 (ArrayList<TowerPiece> pieces){
        //ArrayList<Float> numberSet = ReadFile.read(filename);
        ArrayList<float[]> currSet = new ArrayList<>();
//    	float empty[] = new float [puzzle1GroupSize];
//    	currSet.add(0, empty);
//        currSet.add(1, empty);
//        currSet.add(2, empty);
//        currSet.add(3, empty);
        for(int i = 0; i<10; i++)
        {
        	ArrayList<TowerPiece> shufflePieces = pieces;
        	Collections.shuffle(shufflePieces, new Random());
        	int x = new Random().nextInt(shufflePieces.size()-1)+1;
        	int y = new Random().nextInt(x);
        	//List<TowerPiece> selectPieces = shufflePieces.subList(y, x);
        	ArrayList<TowerPiece> out = new ArrayList<TowerPiece>();
//        	for (TowerPiece tp : shufflePieces)
//        	{
//        		if (!(selectPieces.contains(tp)))
//        		{
//        			out.add(tp);
//z
//        		}
//        	}
        	//System.out.print(shufflePieces);
        	Tower towerX  = new Tower(new ArrayList<TowerPiece>(), shufflePieces);
        	population_puzzle2.add(towerX);
        	//towerX.printout();
        	System.out.print("\n");
        }
        
        
//        for(Tower t : population_puzzle2)
//        {
//        	//Collections.shuffle(pieces);
////        	Tower towerX  = new Tower(pieces);
////        	population_puzzle2.add(towerX);
//        	t.printout();
//        	System.out.print("xxxFitness: " + checkTowerFitness(t));
//        	System.out.print("\n\n");
//        }
        System.out.print("Pop Size:" + population_puzzle2.size());
        //for(int i = 0; i < binCount; i++){
        	//float arr[] = new float [puzzle1GroupSize];
            //for(int j = 0; j < puzzle1GroupSize; j++){
                //arr[j] = numberSet.remove(0);	//add the head
            //}
            //currSet.set(i, arr);
        //}
        //population.add(currSet);
    }
    /*
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
    */
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

    public static void replaceLeastFit(ArrayList<float[]> child1, ArrayList<float[]> child2){
        int ind = getLeastFitP1();
        int secInd = get2ndLeastFitP1();
        population.set(ind, child1);
        population.set(secInd, child2);
    }
    public static void replaceLeastFit_P2(Tower child1, Tower child2){
        int ind = getLeastFitP2();
        int secInd = get2ndLeastFitP2();
        population_puzzle2.set(ind, child1);
        population_puzzle2.set(secInd, child2);
        System.out.println("Crossover");
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
            int var = list.size();
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
        replaceLeastFit(child1, child2);
    }

    /*
     * The parent 1 is defined as having lesser or equal size to parent 2
     */
    public static void genOffSpring_P2(Tower parent1, Tower parent2){
    	Tower child1 = parent1;
    	Tower child2 = parent2;
    	TowerPiece temp;
    	int size1 = parent1.tower.size();
    	int size2 = parent2.tower.size();
    	int lowerSize =(size1>size2) ? (size2):(size1);	
        int crossoverPoint = (int) Math.floor(Math.random() * lowerSize);
        if (lowerSize == 0)
        {
        	//
        }
        else
        {
        	for(int b = 0; b < crossoverPoint; b++) {									//crossover
	            temp = child1.tower.get(b);
	            child1.tower.set(b, parent2.tower.get(b)); 
	            child2.tower.set(b, temp);
        	}
        }
        
        //child1 = fixErrors(child1);
        //child2 = fixErrors(child2);
        replaceLeastFit_P2(child1, child2);
    }
    /*
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
    } */
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
    public static int getFittestP2(){
        float maxFitValue = Integer.MIN_VALUE;
        int bestFitIndex = 0;
        for(int i = 0; i < population_puzzle2.size(); i++){
            float currFit = 0;
            currFit = checkTowerFitness(population_puzzle2.get(i));
            if (currFit > maxFitValue){
                bestFitIndex = i;
                maxFitValue = currFit;
            }
        }
        return bestFitIndex;
    }
    public static int get2ndFittestP2(){
        float maxFitValue = Integer.MIN_VALUE;
        int bestFitIndex = 0;
        int second_bestFitIndex = 0;
        float second_maxFitValue = Integer.MIN_VALUE;
        for(int i = 0; i < population_puzzle2.size(); i++){
            float currFit = 0;
            currFit = (population_puzzle2.get(i).calcScore());
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
    public static int getLeastFitP2(){
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
    public static int get2ndLeastFitP2(){
    	
    	float minFitValue = Integer.MAX_VALUE;
        int bestFitIndex = 0;
        int second_bestFitIndex = 0;
        float second_minFitValue = Integer.MAX_VALUE;
        for(int i = 0; i < population_puzzle2.size(); i++){
            float currFit = 0;
            currFit = population_puzzle2.get(i).calcScore();
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
    /*
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
    */
    
    private static int checkTowerFitness(Tower tower) {
		
		return tower.calcScore();
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
    public static void mutate_puzzle2(String operation)
    {
    	switch(operation)
    	{
			case("add"):
			{
				Random rand = new Random();
		    	TowerPiece swapper1;
		    	TowerPiece swapper2;
		    	
		        int mutated = rand.nextInt(population_puzzle2.size());
		        //int mutationCount = rand.nextInt(5);
		        population_puzzle2.get(mutated).putRandom();
		        break;
			}
			case("remove"):
			{
				Random rand = new Random();
		    	TowerPiece swapper1;
		    	TowerPiece swapper2;
		    	
		        int mutated = rand.nextInt(population_puzzle2.size());
		        //int mutationCount = rand.nextInt(5);
		        population_puzzle2.get(mutated).pullRandom();
		        break;
			}
			case("swap"):
			{
				Random rand = new Random();
		    	TowerPiece swapper1;
		    	TowerPiece swapper2;
		    	
		        int mutated = rand.nextInt(population_puzzle2.size());
		        int mutationCount = rand.nextInt(5);
		        for (int i = 0; i < mutationCount; i++){
		
		            int mutationPoint = rand.nextInt(population_puzzle2.get(mutated).tower.size());		//TODO rand.nextInt( )
		            int mutationPoint2 = rand.nextInt(population_puzzle2.get(mutated).tower.size());
		            swapper1 = population_puzzle2.get(mutated).tower.get(mutationPoint);
		            swapper2 = population_puzzle2.get(mutated).tower.get(mutationPoint2);
		            population_puzzle2.get(mutated).tower.set(mutationPoint, swapper2);
		            population_puzzle2.get(mutated).tower.set(mutationPoint2, swapper1);
			}
		        break;
		    }
    	}
    }
    /*(
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
    */
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
            case 2: {
            	String timer = args[2];
            	puzzle2(timer);
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
    public static void puzzle2(String timer)
    {
    	System.out.println("this is puzzle 2.");
    	ArrayList<TowerPiece> pieces = ReadFile.read2(filename);
    	Random rand = new Random();
    	genPop_puzzle2(pieces);
    	int genCount = 0;
    	float startTime = System.nanoTime();
    	float checkTime = (Float.parseFloat(timer)) * 1000000000;
    	float endTime = 0;
    	while (endTime - startTime < checkTime) {
    		System.out.println("Generation: " + genCount + " Fitness " + checkTowerFitness(population_puzzle2.get(getFittestP2())));
    		if (bestScore < checkTowerFitness(population_puzzle2.get(getFittestP2()))) {
    			bestScore = checkTowerFitness(population_puzzle2.get(getFittestP2()));
    			bestGen = genCount;
    			//Iterator<Tower> iterator = population_puzzle2.iterator();

    			bestTower = new Tower(population_puzzle2.get(getFittestP2()));
    			System.out.print("NEW BEST (not zero): " + population_puzzle2.get(getFittestP2()).calcScore()+"\n");
    			bestTower.printout();
    			System.out.print("Fitness: " + checkTowerFitness(bestTower));
    			System.out.print("\n\n");
    			//              		return;
    			//                 if(bestScore > 0)
    			//                 {
    			//                	 population_puzzle2.get(getFittestP2()).printout();
    			//                	 System.out.print("Fitness: " + population_puzzle2.get(getFittestP2()).calcScore());
    			//                  	System.out.print("\n\n");
    			//                	 return;
    			//                 }
    		}
    		if (rand.nextInt() % 15 > 12) {
    			mutate_puzzle2("remove");
    			System.out.println("* Mutation - REMOVE*");
    		}
    		else if (rand.nextInt() % 15 > 10) {
    			mutate_puzzle2("add");
    			System.out.println("* Mutation - ADD *");
    		}
    		else if (rand.nextInt() % 15 > 8) {
    			mutate_puzzle2("add");
    			System.out.println("* Mutation - SWAP *");
    		}
    		Tower parent1 = population_puzzle2.get(getFittestP2());
    		Tower parent2 = population_puzzle2.get(get2ndFittestP2());
    		genOffSpring_P2(parent1, parent2);
    		genCount++;
    		endTime = System.nanoTime();
    	}
    	/*for(Tower t : population_puzzle2)			//printout CURRENT POPulation
        {
        	//Collections.shuffle(pieces);
//        	Tower towerX  = new Tower(pieces);
//        	population_puzzle2.add(towerX);
        	t.printout();
        	System.out.print("Fitness: " + checkTowerFitness(t));
        	System.out.print("\n\n");
        }*/
    	System.out.println("Pop Size:" + population_puzzle2.size());
    	System.out.println("Ran for: " + timer + " seconds");
    	System.out.println("Solution found at in Generation: " + bestGen + " at fitness: " + bestScore);
    	bestTower.printout();
    	System.out.print("Fitness: " + checkTowerFitness(bestTower));
    	System.out.print("\n\n");
        
         //System.out.println("IS ANYTHING HERE ^^^^^^^^^");
    }
	
}
