import java.util.*;
public class geneticAlgo {
    static int PopSize = 100;
    static int groupSize = 15;
    static ArrayList<int[]> population = new ArrayList<int[]>();
    static ArrayList<int[]> pastPopulation = new ArrayList<int[]>();
    int fittest[] = {1,1,1,1,1,1,1,1,1,1};
    public static void genPop (){
        for(int i = 0; i < PopSize; i++){
            int arr[] = new int [groupSize];
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
    public static int checkFit(ArrayList<int[]> pop) {
        int maxFit = 0;
        for (int i = 0; i < pop.size(); i++) {
            int currFit = 0;
            for (int j = 0; j < pop.get(i).length; j++) {
                if (pop.get(i)[j] == 1) {
                    currFit++;
                }
            }
            if(maxFit < currFit){
                maxFit = currFit;
            }
        }
        return maxFit;
    }
    public static void replaceLeastFit(int[] child1, int[] child2){
        pastPopulation = population;
        int ind = getLeastFit();
        int secInd = get2ndLeastFit();
        population.set(ind, child1);
        population.set(secInd, child2);
    }
    public static void genOffSpring(int[] parent1, int[] parent2){
        int[] child1 = parent1;
        int[] child2 = parent2;
        int crossoverPoint = (int) Math.floor(Math.random() * parent1.length);
        for(int i = 0; i < crossoverPoint; i++){
            child1[i] = parent2[i];
            child2[i] = parent1[i];
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
        Random rand = new Random();
        genPop();
        int genCount = 0;
        while (checkFit(population) < 15) {
            System.out.println("Generation: " + genCount + " Fitness: " + checkFit(population));
            if(rand.nextInt()%9 < 6){
                mutate();
            }
            int[] parent1 = population.get(getFittest());
            int[] parent2 = population.get(get2ndFittest());
            genOffSpring(parent1, parent2);
            genCount++;
        }
        System.out.println("Solution found at in Generation: " + genCount + " at fitness: " + checkFit(population));
    }
}
