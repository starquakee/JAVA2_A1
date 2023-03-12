import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OnlineCoursesAnalyzer {
    private BufferedReader br;
    private List<String> list = new ArrayList<String>();
    public List<String> Institution = new ArrayList<>();
    public List<String> CourseBF_Number = new ArrayList<>();
    public List<String> Launch_Date = new ArrayList<>();
    public List<String> CourseBF_Title = new ArrayList<>();
    public List<String> Instructors = new ArrayList<>();
    public List<String> CourseBF_Subject = new ArrayList<>();
    public List<Integer> Year = new ArrayList<>();
    public List<Integer> Honor_Code_Certificates = new ArrayList<>();
    public List<Integer> Participants = new ArrayList<>();
    public List<Integer> Audited = new ArrayList<>();
    public List<Integer> Certified = new ArrayList<>();
    public List<Double> p_Audited = new ArrayList<>();
    public List<Double> p_Certified = new ArrayList<>();
    public List<Double> p_Certified_50 = new ArrayList<>();
    public List<Double> p_Played_Video = new ArrayList<>();
    public List<Double> p_Posted_in_Forum = new ArrayList<>();
    public List<Double> p_Grade_Higher_Than_Zero = new ArrayList<>();
    public List<Double> Total_CourseBF_Hours = new ArrayList<>();
    public List<Double> Median_Hours_for_Certification = new ArrayList<>();
    public List<Double> Median_Age = new ArrayList<>();
    public List<Double> p_Male = new ArrayList<>();
    public List<Double> p_Female = new ArrayList<>();
    public List<Double> p_Bachelor = new ArrayList<>();





    public OnlineCoursesAnalyzer(String fileName) throws Exception {
        br = new BufferedReader(new FileReader(fileName));

        String stemp;
        while ((stemp = br.readLine()) != null) {
            list.add(stemp);
        }
        int rowNum = this.getRowNum();
        int colNum = this.getColNum();
//        System.out.println("rowNum:" + rowNum);
//        System.out.println("colNum:" + colNum);

        for(int i=1;i<rowNum;i++){
            this.Institution.add(this.getString(i, 0));
            this.CourseBF_Number.add(this.getString(i, 1));
            this.Launch_Date.add(this.getString(i, 2));
            if (this.getString(i, 3).contains("\"")){
                this.CourseBF_Title.add(this.getString(i, 3).split("\"")[1]);
            }else {
                this.CourseBF_Title.add(this.getString(i, 3));
            }
            if (this.getString(i, 4).contains("\"")){
                this.Instructors.add(this.getString(i, 4).split("\"")[1]);
            }else {
                this.Instructors.add(this.getString(i, 4));
            }
            if (this.getString(i, 5).contains("\"")){
                this.CourseBF_Subject.add(this.getString(i, 5).split("\"")[1]);
            }else {
                this.CourseBF_Subject.add(this.getString(i, 5));
            }
            this.Year.add(Integer.parseInt(this.getString(i, 6)));
            this.Honor_Code_Certificates.add(Integer.parseInt(this.getString(i, 7)));
            this.Participants.add(Integer.parseInt(this.getString(i, 8)));
            this.Audited.add(Integer.parseInt(this.getString(i, 9)));
            this.Certified.add(Integer.parseInt(this.getString(i, 10)));
            this.p_Audited.add(Double.parseDouble(this.getString(i, 11)));
            this.p_Certified.add(Double.parseDouble(this.getString(i, 12)));
            this.p_Certified_50.add(Double.parseDouble(this.getString(i, 13)));
            this.p_Played_Video.add(Double.parseDouble(this.getString(i, 14)));
            this.p_Posted_in_Forum.add(Double.parseDouble(this.getString(i, 15)));
            this.p_Grade_Higher_Than_Zero.add(Double.parseDouble(this.getString(i, 16)));
            this.Total_CourseBF_Hours.add(Double.parseDouble(this.getString(i, 17)));
            this.Median_Hours_for_Certification.add(Double.parseDouble(this.getString(i, 18)));
            this.Median_Age.add(Double.parseDouble(this.getString(i, 19)));
            this.p_Male.add(Double.parseDouble(this.getString(i, 20)));
            this.p_Female.add(Double.parseDouble(this.getString(i, 21)));
            this.p_Bachelor.add(Double.parseDouble(this.getString(i, 22)));


        }
    }

    public int getRowNum() {
        return list.size();
    }

    public int getColNum() {
        if (!list.toString().equals("[]")) {
            if (list.get(0).toString().contains(",")) {
                return list.get(0).toString().split(",").length;
            } else if (list.get(0).toString().trim().length() != 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }



    public String getString(int row, int col) {
        String temp;
        int colnum = this.getColNum();
        if (colnum > 1) {
            temp = list.get(row).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")[col];;
        } else if(colnum == 1){
            temp = list.get(row).toString();
        } else {
            temp = null;
        }
        return temp;
    }

    public Map<String, Integer> getPtcpCountByInst(){
        List<String> Ins_par = new ArrayList<>();
//        System.out.println(list.size());
        for (int i=0;i<list.size()-1;i++){
            Ins_par.add(Institution.get(i)+"!"+Participants.get(i));
//            System.out.println(Institution.get(i)+"!"+Participants.get(i));
        }
        Stream<String> stream = Ins_par.stream();
        Map<String, Integer> map1 = stream.collect(Collectors.groupingBy(s->s.split("!")[0],Collectors.summingInt(s->Integer.parseInt(s.split("!")[1]))));

        List<Map.Entry<String, Integer>> entryList1 = new ArrayList<Map.Entry<String, Integer>>(map1.entrySet());
        Collections.sort(entryList1, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
                return me1.getKey().compareTo(me2.getKey());

            }
        });
        Map<String, Integer> ans = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> stringIntegerEntry : entryList1) {
            ans.put(stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
        }
        return ans;

    }
    public Map<String, Integer> getPtcpCountByInstAndSubject(){
        List<String> Ins_par = new ArrayList<>();
        for (int i=0;i<list.size()-1;i++){
            Ins_par.add(Institution.get(i)+"-"+CourseBF_Subject.get(i)+"!"+Participants.get(i));
        }
        Stream<String> stream = Ins_par.stream();
        Map<String, Integer> map1 = stream.collect(Collectors.groupingBy(s->s.split("!")[0],Collectors.summingInt(s->Integer.parseInt(s.split("!")[1]))));
        List<Map.Entry<String, Integer>> entryList1 = new ArrayList<Map.Entry<String, Integer>>(map1.entrySet());
        Collections.sort(entryList1, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
                return me2.getValue().compareTo(me1.getValue());

            }
        });
//        System.out.println(entryList1.get(0).getKey());
        Map<String, Integer> ans = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> stringIntegerEntry : entryList1) {
            ans.put(stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
        }
        return ans;
    }
    public Map<String, List<List<String>>> getCourseListOfInstructor(){
        Map<String,Set<String>> map_independent=new HashMap<>();
        Map<String,Set<String>> map_codeveloped=new HashMap<>();
        for (int i=0;i<list.size()-1;i++){
            if(!Instructors.get(i).contains(", ")){//independent
                if (map_independent.containsKey(Instructors.get(i))) {
                    Set<String> set= map_independent.get(Instructors.get(i));
                    set.add(CourseBF_Title.get(i));
                    map_independent.replace(Instructors.get(i), set);
                } else {
                    Set<String> set=new HashSet<>();
                    set.add(CourseBF_Title.get(i));
                    map_independent.put(Instructors.get(i), set);
                }
            }else {//codeveloped
                for(int j=0;j<Instructors.get(i).split(", ").length;j++){
                    if (map_codeveloped.containsKey(Instructors.get(i).split(", ")[j])) {
                        Set<String> set= map_codeveloped.get(Instructors.get(i).split(", ")[j]);
                        set.add(CourseBF_Title.get(i));
                        map_codeveloped.replace(Instructors.get(i).split(", ")[j], set);
                    }else {
                        Set<String> set=new HashSet<>();
                        set.add(CourseBF_Title.get(i));
                        map_codeveloped.put(Instructors.get(i).split(", ")[j], set);
                    }
                }
            }
        }
//        map_independent.forEach((s,u)->System.out.println(s+u));
//        map_codeveloped.forEach((s,u)->System.out.println(s+u));
//        List<String> list_independent = new ArrayList<String> ();
//        List<String> list_codeveloped = new ArrayList<String> ();
        Map<String, List<List<String>>> ans = new HashMap<>();
        for (int i=0;i<list.size()-1;i++){
            if(!Instructors.get(i).contains(", ")){//independent
                if (!ans.containsKey(Instructors.get(i))) {
                    List<String> list_independent = new ArrayList<> ();
                    List<String> list_codeveloped = new ArrayList<> ();

                    if(map_independent.containsKey(Instructors.get(i))){
                        list_independent.addAll(map_independent.get(Instructors.get(i)));
                        Collections.sort(list_independent);
                    }
                    if(map_codeveloped.containsKey(Instructors.get(i))){
                        list_codeveloped.addAll(map_codeveloped.get(Instructors.get(i)));
                        Collections.sort(list_codeveloped);
                    }

                    List<List<String>> list_ans = new ArrayList<> ();
                    list_ans.add(list_independent);
                    list_ans.add(list_codeveloped);
                    ans.put(Instructors.get(i), list_ans);
                }
            }else {//codeveloped
                for(int j=0;j<Instructors.get(i).split(", ").length;j++){
                    if (!ans.containsKey(Instructors.get(i).split(", ")[j])) {
                        List<String> list_independent = new ArrayList<> ();
                        List<String> list_codeveloped = new ArrayList<> ();
                        if(map_independent.containsKey(Instructors.get(i).split(", ")[j])){
                            list_independent.addAll(map_independent.get(Instructors.get(i).split(", ")[j]));
                            Collections.sort(list_independent);
                        }
                        if(map_codeveloped.containsKey(Instructors.get(i).split(", ")[j])){
                            list_codeveloped.addAll(map_codeveloped.get(Instructors.get(i).split(", ")[j]));
                            Collections.sort(list_codeveloped);
                        }
                        List<List<String>> list_ans = new ArrayList<> ();
                        list_ans.add(list_independent);
                        list_ans.add(list_codeveloped);
                        ans.put(Instructors.get(i).split(", ")[j], list_ans);
                    }
                }
            }
        }
//        ans.forEach((u,v)->System.out.println(u+v));
        return ans;
    }
    public List<String> getCourses(int topK, String by){
        List<String> Ins_par = new ArrayList<>();
        List<String> ans = new ArrayList<>();
        for (int i=0;i<list.size()-1;i++){
            Ins_par.add(CourseBF_Title.get(i)+"!"+Total_CourseBF_Hours.get(i)+"!"+Participants.get(i));
//            System.out.println(CourseBF_Title.get(i)+"!"+Total_CourseBF_Hours.get(i));
        }
        Stream<String> stream = Ins_par.stream();
//        Map<String, Double> map1 = stream.collect(Collectors.groupingBy(s->s.split("!")[0],Collectors.maxBy(Comparator.comparing(s->Double.parseDouble(s.split("!")[1]))))))
//        Map<String,List<String>> map = list.stream().collect(Collectors.groupingBy(s->s.split("!")[0]));
        Map<String, DoubleSummaryStatistics> collect = null;
        if(Objects.equals(by, "hours")){
            collect = stream.collect(
                    Collectors.groupingBy(s->s.split("!")[0],
                            Collectors.summarizingDouble(s->Double.parseDouble(s.split("!")[1]))));
        }else if(Objects.equals(by, "participants")){
            collect = stream.collect(
                    Collectors.groupingBy(s->s.split("!")[0],
                            Collectors.summarizingDouble(s->Double.parseDouble(s.split("!")[2]))));
        }

        for (int i=0;i<topK;i++){
            double max = -111111111;
            String max_name = "";
            Map.Entry<String, DoubleSummaryStatistics> todelete = null;
            for (Map.Entry<String, DoubleSummaryStatistics> entry : collect.entrySet()) {
                DoubleSummaryStatistics doubleSummaryStatistics = entry.getValue();
                if(doubleSummaryStatistics.getMax()>max){
                    max = doubleSummaryStatistics.getMax();
                    todelete = entry;
                    max_name = entry.getKey();
                }
            }
//            System.out.println(max);
            ans.add(max_name);
            collect.entrySet().remove(todelete);
        }
        return ans;
    }
    public List<String> searchCourses(String courseSubject, double
            percentAudited, double totalCourseHours){
        Set<String> set = new HashSet<>();
        List<String> Ins_par = new ArrayList<>();
        for (int i=0;i<list.size()-1;i++){
            Ins_par.add(CourseBF_Subject.get(i)+"!"+p_Audited.get(i)+"!"+Total_CourseBF_Hours.get(i)+"!"+CourseBF_Title.get(i));
        }
        Stream<String> stream = Ins_par.stream();
        set = stream.filter(s->Double.parseDouble(s.split("!")[1])>=percentAudited
                &&Double.parseDouble(s.split("!")[2])<=totalCourseHours
                &&s.split("!")[0].toLowerCase().contains(courseSubject.toLowerCase())).collect(Collectors.toSet());
        List<String> ans = new ArrayList<>();
        for(String value: set){
            ans.add(value.split("!")[3]);
        }

        Collections.sort(ans);
        return ans.stream().distinct().collect(Collectors.toList());
    }

    public List<String> recommendCourses(int age, int gender, int
            isBachelorOrHigher){
        List<String> Ins_par = new ArrayList<>();
        for (int i=0;i<list.size()-1;i++){
            Ins_par.add(CourseBF_Number.get(i)+"!"+Median_Age.get(i)+"!"+p_Male.get(i)+"!"+p_Bachelor.get(i));
        }
        Stream<String> stream = Ins_par.stream();
        Map<String, Double> map_age = stream.collect(Collectors.groupingBy(s->s.split("!")[0],Collectors.averagingDouble(s->Double.parseDouble(s.split("!")[1]))));
        stream = Ins_par.stream();
        Map<String, Double> map_male = stream.collect(Collectors.groupingBy(s->s.split("!")[0],Collectors.averagingDouble(s->Double.parseDouble(s.split("!")[2]))));
        stream = Ins_par.stream();
        Map<String, Double> map_bachelor = stream.collect(Collectors.groupingBy(s->s.split("!")[0],Collectors.averagingDouble(s->Double.parseDouble(s.split("!")[3]))));
        Map<String, Double> map_simi = new HashMap<>();
        for(String key : map_age.keySet()){
            double similarity = Math.pow((double) age-map_age.get(key),2)+Math.pow((double) gender*100-map_male.get(key),2)+Math.pow((double) isBachelorOrHigher*100-map_bachelor.get(key),2);
            map_simi.put(key, similarity);
        }
        List<String> list_num = new ArrayList<>();
        System.out.println(map_simi);
        for(int i=0;i<10;i++){
            double min = 99999999;
            String min_key="";
            for(String key : map_simi.keySet()){
                if(map_simi.get(key)<min){
                    min = map_simi.get(key);
                    min_key=key;
                }
            }
            list_num.add(min_key);
            map_simi.remove(min_key);
        }
        System.out.println(list_num);
        List<String> ans = new ArrayList<>();
        for(int j=0;j<10;j++){
            int max = 0;
            String max_title="";
            for (int i=0;i<list.size()-1;i++){
                if(Objects.equals(CourseBF_Number.get(i), list_num.get(j))){
                    int days = Integer.parseInt(Launch_Date.get(i).split("/")[0])*31+
                                Integer.parseInt(Launch_Date.get(i).split("/")[1])
                            +Integer.parseInt(Launch_Date.get(i).split("/")[2])*365;
                    if (days>max){
                        max = days;
                        max_title = CourseBF_Title.get(i);
                    }

                }
            }
            ans.add(max_title);

        }
        return ans;

    }


    public static void main(String[] args)throws Exception {
        OnlineCoursesAnalyzer util = new OnlineCoursesAnalyzer("local.csv");
//        System.out.println(util.getPtcpCountByInst());
//        System.out.println(util.getPtcpCountByInstAndSubject());
//        util.getCourseListOfInstructor().forEach((s,v)->System.out.println(s+v));
//        System.out.println(util.getCourseListOfInstructor());
//        System.out.println(util.getCourses(15,"participants"));
//        util.searchCourses("SCIENCE", 25.0, 400).forEach(System.out::println);
//        System.out.println(util.recommendCourses(25, 1, 1));
        util.recommendCourses(25,1,1).forEach(System.out::println);

    }
}