package com.example.project2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class connectionAlgorithm {

    private Graph addConnections(Graph railGraph){
        railGraph.addVertex("Metro Center",
                Arrays.asList(
                        //new Vertex("Farragut North", 1),
                        //new Vertex("Gallery Pl-Chinatown", 1),
                        new Vertex("McPherson Square", 1),
                        new Vertex("Federal Triangle", 1)
                )
        );
        // railGraph.addVertex("Gallery Pl-Chinatown",
        //                     Arrays.asList(
        //                         new Vertex("Metro Center", 1),
        //                         new Vertex("Archives-Navy Memorial-Penn Quarter", 1),
        //                         new Vertex("Mt Vernon Sq 7th St-Convention Center", 1),
        //                         new Vertex("Judiciary Square", 1)
        //                     )
        // );
        railGraph.addVertex("L'Enfant Plaza",
                Arrays.asList(
                        new Vertex("Smithsonian", 1),
                        //new Vertex("Archives-Navy Memorial-Penn Quarter", 1),
                        new Vertex("Pentagon", 1),
                        //new Vertex("Waterfront", 1),
                        new Vertex("Federal Center SW", 1)
                )
        );
        // railGraph.addVertex("Fort Totten",
        //                     Arrays.asList(
        //                         new Vertex("Takoma", 1),
        //                         new Vertex("West Hyattsville", 1),
        //                         new Vertex("Brookland-CUA", 1),
        //                         new Vertex("Georgia Ave-Petworth", 1)
        //                     )
        // );
        railGraph.addVertex("Rosslyn",
                Arrays.asList(
                        new Vertex("Foggy Bottom-GWU", 1),
                        new Vertex("Court House", 1),
                        new Vertex("Arlington Cemetery", 1)
                )
        );
        railGraph.addVertex("Pentagon",
                Arrays.asList(
                        new Vertex("Pentagon City", 1),
                        new Vertex("L'Enfant Plaza", 1),
                        new Vertex("Arlington Cemetery", 1)
                )
        );
        railGraph.addVertex("Stadium-Armory",
                Arrays.asList(
                        new Vertex("Potomac Ave", 1),
                        new Vertex("Benning Road", 1),
                        new Vertex("Minnesota Ave", 1)
                )
        );
        railGraph.addVertex("King St-Old Town",
                Arrays.asList(
                        //new Vertex("Eisenhower Avenue", 1),
                        new Vertex("Braddock Road", 1),
                        new Vertex("Van Dorn Street", 1)
                )
        );
        return railGraph;
    }

    private Graph addBlueLine(Graph railGraph){
        railGraph.addVertex("Franconia-Springfield",
                Arrays.asList(
                        new Vertex("Van Dorn Street", 1)
                )
        );
        railGraph.addVertex("Van Dorn Street",
                Arrays.asList(
                        new Vertex("Franconia-Springfield", 1),
                        new Vertex("King St-Old Town", 1)
                )
        );
        railGraph.addVertex("Braddock Road",
                Arrays.asList(
                        new Vertex("King St-Old Town", 1),
                        new Vertex("Ronald Reagan Washington National Airport", 1)
                )
        );
        railGraph.addVertex("Ronald Reagan Washington National Airport",
                Arrays.asList(
                        new Vertex("Braddock Road", 1),
                        new Vertex("Crystal City", 1)
                )
        );
        railGraph.addVertex("Crystal City",
                Arrays.asList(
                        new Vertex("Pentagon City", 1),
                        new Vertex("Ronald Reagan Washington National Airport", 1)
                )
        );
        railGraph.addVertex("Pentagon City",
                Arrays.asList(
                        new Vertex("Crystal City", 1),
                        new Vertex("Pentagon", 1)
                )
        );
        railGraph.addVertex("Arlington Cemetery",
                Arrays.asList(
                        new Vertex("Pentagon", 1),
                        new Vertex("Rosslyn", 1)
                )
        );
        railGraph.addVertex("Benning Road",
                Arrays.asList(
                        new Vertex("Stadium-Armory", 1),
                        new Vertex("Capitol Heights", 1)
                )
        );
        railGraph.addVertex("Capitol Heights",
                Arrays.asList(
                        new Vertex("Benning Road", 1),
                        new Vertex("Addison Road-Seat Pleasant", 1)
                )
        );
        railGraph.addVertex("Addison Road-Seat Pleasant",
                Arrays.asList(
                        new Vertex("Capitol Heights", 1),
                        new Vertex("Morgan Boulevard", 1)
                )
        );
        railGraph.addVertex("Morgan Boulevard",
                Arrays.asList(
                        new Vertex("Addison Road-Seat Pleasant", 1),
                        new Vertex("Largo Town Center", 1)
                )
        );
        railGraph.addVertex("Largo Town Center",
                Arrays.asList(
                        new Vertex("Morgan Boulevard", 1)
                )
        );
        return railGraph;
    }

    private Graph addOrangeLine(Graph railGraph){
        railGraph.addVertex("Vienna/Fairfax-GMU",
                Arrays.asList(
                        new Vertex("Dunn Loring-Merrifield", 1)
                )
        );
        railGraph.addVertex("Dunn Loring-Merrifield",
                Arrays.asList(
                        new Vertex("Vienna/Fairfax-GMU", 1),
                        new Vertex("West Falls Church-VT/UVA", 1)
                )
        );
        railGraph.addVertex("West Falls Church-VT/UVA",
                Arrays.asList(
                        new Vertex("East Falls Church", 1),
                        new Vertex("Dunn Loring-Merrifield", 1)
                )
        );
        railGraph.addVertex("East Falls Church",
                Arrays.asList(
                        new Vertex("West Falls Church-VT/UVA", 1),
                        new Vertex("Ballston-MU", 1)
                )
        );
        railGraph.addVertex("Ballston-MU",
                Arrays.asList(
                        new Vertex("Virginia Square-GMU", 1),
                        new Vertex("East Falls Church", 1)
                )
        );
        railGraph.addVertex("Virginia Square-GMU",
                Arrays.asList(
                        new Vertex("Ballston-MU", 1),
                        new Vertex("Clarendon", 1)
                )
        );
        railGraph.addVertex("Clarendon",
                Arrays.asList(
                        new Vertex("Virginia Square-GMU", 1),
                        new Vertex("Court House", 1)
                )
        );
        railGraph.addVertex("Court House",
                Arrays.asList(
                        new Vertex("Clarendon", 1),
                        new Vertex("Rosslyn", 1)
                )
        );
        railGraph.addVertex("Foggy Bottom-GWU",
                Arrays.asList(
                        new Vertex("Rosslyn", 1),
                        new Vertex("Farragut West", 1)
                )
        );
        railGraph.addVertex("Farragut West",
                Arrays.asList(
                        new Vertex("Foggy Bottom-GWU", 1),
                        new Vertex("McPherson Square", 1)
                )
        );
        railGraph.addVertex("McPherson Square",
                Arrays.asList(
                        new Vertex("Farragut West", 1),
                        new Vertex("Metro Center", 1)
                )
        );
        railGraph.addVertex("Federal Triangle",
                Arrays.asList(
                        new Vertex("Metro Center", 1),
                        new Vertex("Smithsonian", 1)
                )
        );
        railGraph.addVertex("Smithsonian",
                Arrays.asList(
                        new Vertex("Federal Triangle", 1),
                        new Vertex("L'Enfant Plaza", 1)
                )
        );
        railGraph.addVertex("Federal Center SW",
                Arrays.asList(
                        new Vertex("Capitol South", 1),
                        new Vertex("L'Enfant Plaza", 1)
                )
        );
        railGraph.addVertex("Capitol South",
                Arrays.asList(
                        new Vertex("Federal Center SW", 1),
                        new Vertex("Eastern Market", 1)
                )
        );
        railGraph.addVertex("Eastern Market",
                Arrays.asList(
                        new Vertex("Capitol South", 1),
                        new Vertex("Potomac Ave", 1)
                )
        );
        railGraph.addVertex("Potomac Ave",
                Arrays.asList(
                        new Vertex("Eastern Market", 1),
                        new Vertex("Stadium-Armory", 1)
                )
        );
        railGraph.addVertex("Minnesota Ave",
                Arrays.asList(
                        new Vertex("Stadium-Armory", 1),
                        new Vertex("Deanwood", 1)
                )
        );
        railGraph.addVertex("Deanwood",
                Arrays.asList(
                        new Vertex("Minnesota Ave", 1),
                        new Vertex("Cheverly", 1)
                )
        );
        railGraph.addVertex("Cheverly",
                Arrays.asList(
                        new Vertex("Deanwood", 1),
                        new Vertex("Landover", 1)
                )
        );
        railGraph.addVertex("Landover",
                Arrays.asList(
                        new Vertex("Cheverly", 1),
                        new Vertex("New Carrollton", 1)
                )
        );
        railGraph.addVertex("New Carrollton",
                Arrays.asList(
                        new Vertex("Landover", 1)
                )
        );
        return railGraph;
    }


    private Graph createMetroGraph(){
        //Creation date: April 13, 2021
        Graph railGraph = new Graph();
        railGraph = addConnections(railGraph);
        railGraph = addOrangeLine(railGraph);
        railGraph = addBlueLine(railGraph);
        return railGraph;
    }

    public String determinePath(String startStation, String endStation){
        System.out.println("Start:" + startStation + ",End:" + endStation +".");
        Graph g = createMetroGraph();
        List path = g.getShortestPath(endStation.toString(), startStation.toString());
        String toReturn = "";
        for (int i=0; i<path.size(); i++){
            toReturn = toReturn + path.get(i) + ";";
        }
        return toReturn;
    }

    public connectionAlgorithm(){
        System.out.println("Activated");
    }

}

class Vertex implements Comparable<Vertex> {

    private String id;
    private Integer distance;
    //private String line;

    public Vertex(String id, Integer distance) {
        super();
        this.id = id;
        this.distance = distance;
        //this.line = line;
    }

    public String getId() {
        return id;
    }

    public Integer getDistance() {
        return distance;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((distance == null) ? 0 : distance.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (distance == null) {
            if (other.distance != null)
                return false;
        } else if (!distance.equals(other.distance))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vertex [id=" + id + ", distance=" + distance + "]";
    }

    @Override
    public int compareTo(Vertex o) {
        if (this.distance < o.distance)
            return -1;
        else if (this.distance > o.distance)
            return 1;
        else
            return this.getId().compareTo(o.getId());
    }

}

class Graph {

    private final Map<String, List<Vertex>> vertices;

    public Graph() {
        this.vertices = new HashMap<String, List<Vertex>>();
    }

    public void addVertex(String String, List<Vertex> vertex) {
        this.vertices.put(String, vertex);
    }

    public List<String> getShortestPath(String start, String finish) {
        final Map<String, Integer> distances = new HashMap<String, Integer>();
        final Map<String, Vertex> previous = new HashMap<String, Vertex>();
        PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>();

        for(String vertex : vertices.keySet()) {
            if (vertex == start) {
                distances.put(vertex, 0);
                nodes.add(new Vertex(vertex, 0));
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
                nodes.add(new Vertex(vertex, Integer.MAX_VALUE));
            }
            previous.put(vertex, null);
        }

        while (!nodes.isEmpty()) {
            Vertex smallest = nodes.poll();
            if (smallest.getId() == finish) {
                final List<String> path = new ArrayList<String>();
                while (previous.get(smallest.getId()) != null) {
                    path.add(smallest.getId());
                    smallest = previous.get(smallest.getId());
                }
                return path;
            }

            if (distances.get(smallest.getId()) == Integer.MAX_VALUE) {
                break;
            }

            for (Vertex neighbor : vertices.get(smallest.getId())) {
                Integer alt = distances.get(smallest.getId()) + neighbor.getDistance();
                // System.out.println("\nNew");
                // System.out.println(neighbor);
                // System.out.println(smallest);
                if (alt < distances.get(neighbor.getId())) {
                    distances.put(neighbor.getId(), alt);
                    previous.put(neighbor.getId(), smallest);

                    forloop:
                    for(Vertex n : nodes) {
                        if (n.getId() == neighbor.getId()) {
                            nodes.remove(n);
                            n.setDistance(alt);
                            nodes.add(n);
                            break forloop;
                        }
                    }
                }
            }
        }

        return new ArrayList<String>(distances.keySet());
    }

}
