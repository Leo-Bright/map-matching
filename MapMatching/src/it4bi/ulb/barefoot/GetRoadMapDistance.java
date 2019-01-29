package it4bi.ulb.barefoot;

import com.bmwcarit.barefoot.roadmap.Road;
import com.bmwcarit.barefoot.roadmap.RoadMap;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GetRoadMapDistance {
    public static void main(String[] args) {

        String config_file = "D:\\Project\\ideaProjects\\map-matching\\MapMatching\\data\\road-types.json";

        String input = "D:\\Project\\ideaProjects\\map-matching\\MapMatching\\data\\tokyo.network";

        String output = "D:\\Project\\ideaProjects\\map-matching\\MapMatching\\data\\tokyo_distance.network";

        MapMatchingCore core = new MapMatchingCore();

        RoadMap map = core.getRoadMap(config_file);

        GetRoadMapDistance instance = new GetRoadMapDistance();

        instance.getDistance(input, output, map);

    }

    private void getDistance(String input, String output, RoadMap map){
        Iterator<Road> it = map.edges();
        Map<Long, Map<Long, Float>> road2length = new HashMap<>();
        while(it.hasNext()){
            Road _road = it.next();
            long source = _road.source();
            long target = _road.target();
            float length = _road.length();
            Map _hm = road2length.get(source);
            if(null == _hm){
                _hm = new HashMap<Long, Float>();
            }
            _hm.put(target, length);
            road2length.put(source, _hm);
        }
        BufferedReader in = null;
        try{
            in = new BufferedReader(new InputStreamReader(new FileInputStream(input),"utf-8"));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        BufferedWriter out = null;
        try{
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),"utf-8"));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String line = null;
        try{
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                String[] nodes = line.split(" ");
                Long source = Long.parseLong(nodes[0]);
                Long target = Long.parseLong(nodes[1]);

                Float length = road2length.get(source).get(target);
                if(length == null){
//					Map _hm = road2length.get(source);
                    System.out.println(source.toString() + " " + target.toString() + " " + "==!!!===!!!!!!===!!=");
                }else {
                    System.out.println(source.toString() + " " + target.toString() + " " + length.toString());
                    out.write(source.toString() + " " + target.toString() + " " + length.toString());
                    out.newLine();
                }
                //写入相关文件
            }
            out.flush();
        }catch (IOException e){
            System.out.println("Error===================");
            e.printStackTrace();
        }
        System.out.println("done!!=================");


    }
}
