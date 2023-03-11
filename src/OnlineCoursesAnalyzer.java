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
        System.out.println("rowNum:" + rowNum);
        System.out.println("colNum:" + colNum);

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
        System.out.println(list.size());
        for (int i=0;i<list.size()-1;i++){
            Ins_par.add(Institution.get(i)+"!"+Participants.get(i));
            System.out.println(Institution.get(i)+"!"+Participants.get(i));
        }
        Stream<String> stream = Ins_par.stream();
        Map<String, Integer> map1 = stream.collect(Collectors.groupingBy(s->s.split("!")[0],Collectors.summingInt(s->Integer.parseInt(s.split("!")[1]))));

//        List<Map.Entry<String, Integer>> entryList1 = new ArrayList<Map.Entry<String, Integer>>(map1.entrySet());
//        Collections.sort(entryList1, new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
//                return me1.getKey().compareTo(me2.getKey()); // 升序排序
//
//            }
//        });
//        System.out.println("第一种Map排序方式, 根据key排序: \n" + entryList1);
        return map1;

    }


    public static void main(String[] args)throws Exception {
        OnlineCoursesAnalyzer util = new OnlineCoursesAnalyzer("local.csv");
        int rowNum = util.getRowNum();
        int colNum = util.getColNum();
        System.out.println(util.getPtcpCountByInst());

//        System.out.println("rowNum:" + rowNum);
//        System.out.println("colNum:" + colNum);
//
//        for(int i=1;i<rowNum;i++){
//            for(int j=0;j<colNum;j++){
//                System.out.println("result[" + i + "|" + j + "]:" + util.getString(i, j));
//            }
//        }



//        System.out.println(util.getRow(23));

//        String [] userInfos = util.getRow(23).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
//        System.out.println(userInfos.length);
//        for (String userInfo : userInfos) {
//            System.out.println(userInfo);
//        }

    }
}