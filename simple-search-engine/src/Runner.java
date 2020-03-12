//package search;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Runner {
    static List<String> people;
    static Map<String, Set<Integer>> invertedIndexStructure;

    public static Set<Integer> findPerson(String person) {
        //System.out.println("Enter a name or email to search all suitable people.");
        //List<String> peopleOfInterest = new ArrayList<String>();
        Set<Integer> peopleOfInterest = new HashSet<>();

        for (String portion : person.split("\\s+")) {
            for (String k : invertedIndexStructure.keySet()) {
                // retainAll here(cond'n)
                if (k.toLowerCase().equals(portion.toLowerCase())) peopleOfInterest.addAll(invertedIndexStructure.get(k));
            }
        }
        return peopleOfInterest;
    }

    public static void run(Scanner in) {
        boolean runPrompt = true;
        while (runPrompt) {
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");
            switch(Integer.parseInt(in.nextLine())) {
                case 1:
                    int option = 0;
                    while (option == 0) {
                        System.out.println("Select a matching strategy: ALL, ANY, NONE");
                        String selection = in.nextLine();
                        option = (selection.equals("ALL") ? 1 :
                                selection.equals("ANY") ? 2 :
                                        selection.equals("NONE") ? 3 : 0);
                    }
                    System.out.println("Enter a name or email to search all suitable people.");
                    //String person = in.nextLine().toLowerCase();
                    //findPerson(in);
                    Set<Integer> res;
                    if (option == 1) res = findPersonAll(in.nextLine().toLowerCase());
                    else if (option == 2) res = findPersonAny(in.nextLine().toLowerCase());
                    else res = findPersonNone(in.nextLine().toLowerCase());
                    if (res.size() != 0) {
                        System.out.println("Found people:");
                        for (Integer matchingPerson : res) {
                            System.out.println(people.get(matchingPerson));
                        }
                    } else {
                        System.out.println("No matching people found.");
                    }
                    break;
                case 2:
                    System.out.println("=== List of people ===");
                    for (String person : people) {
                        System.out.println(person);
                    }
                    break;
                case 0:
                    runPrompt = false;
                    break;
                default:
                    System.err.println("Incorrect option! Try again.");
                    break;
            }
        }
        //in.nextLine();
        System.out.println("Bye!");
    }

    private static Set<Integer> findPersonAny(String person) {
        // addAll
        return findPerson(person);
    }

    private static Set<Integer> findPersonNone(String person) {
        Set<Integer> result = IntStream.range(0, people.size()).boxed().collect(Collectors.toSet());
        result.removeAll(findPerson(person));
        return result;
    }

    private static Set<Integer> findPersonAll(String person) {
        Set<Integer> peopleOfInterest = new HashSet<>();

        for (String portion : person.split("\\s+")) {
            for (String k : invertedIndexStructure.keySet()) {
                // retainAll here(cond'n)
                if (k.toLowerCase().equals(portion.toLowerCase())) {
                    if (peopleOfInterest.isEmpty()) peopleOfInterest.addAll(invertedIndexStructure.get(k));
                    else peopleOfInterest.retainAll(invertedIndexStructure.get(k));
                }
            }
        }
        return peopleOfInterest;
    }

    public static boolean validatePerson(String potential) {
        String[] entry = potential.trim().split("\\s+");

        if (!entry[0].matches("[a-zA-Z]+") || !entry[1].matches("[a-zA-Z]+")) return false;

        return (entry.length != 3) || entry[2].matches("[a-zA-Z0-9_\\-.]+@[a-z]+\\.(com|ca)");
    }

    public static void addPersonToStructures(String person) {
        people.add(person);
        for (String p : person.split("\\s+")) {
            if (!invertedIndexStructure.containsKey(p)) {
                invertedIndexStructure.put(p, new LinkedHashSet<>(Arrays.asList(people.size()-1)));
            } else {
                invertedIndexStructure.get(p).add(people.size()-1);
            }
        }
    }

    public static void main(String[] args) {
        boolean isDataPassed = false;
        String dataFile = null;
        for (String arg : args) {
            if (isDataPassed) {
                if (dataFile == null) dataFile = arg;
            } else if (arg.equals("--data")) {
                isDataPassed = true;
            }
        }
        people = new ArrayList<>();
        invertedIndexStructure = new LinkedHashMap<>();

        if (dataFile != null) {
            File f = new File(dataFile);
            try (Scanner in = new Scanner(f)) {
                while (in.hasNextLine()) {
                    String person = in.nextLine();
                    if (validatePerson(person)) {
                        addPersonToStructures(person);
                    }
                }
                in.close();
                run(new Scanner(System.in));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Enter the number of people:");
            try (Scanner in = new Scanner(System.in)) {
                int numPeople = Integer.parseInt(in.nextLine());
                System.out.println("Enter all people:");
                for (int i = 0; i < numPeople;) {
                    String person = in.nextLine();
                    if (validatePerson(person)) {
                        addPersonToStructures(person);
                        i++;
                    } else {
                        System.out.println("Incorrect format for user, try again!");
                    }
                }
                run(in);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }
}
