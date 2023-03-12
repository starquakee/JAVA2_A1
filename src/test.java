

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * 基于Java8 分组再统计
 * @author zzg
 *
 */
public class test {

    static List<Fruit> initDate(){
        List<Fruit> list = new ArrayList<Fruit>();

        Fruit one = new Fruit();
        one.setName("苹果一级");
        one.setSid("1");
        one.setPrice(new BigDecimal("123456.98").setScale(BigDecimal.ROUND_HALF_UP, 2) );
        one.setTotal(1100L);
        one.setType("1");


        Fruit two = new Fruit();
        two.setName("苹果二级");
        two.setSid("2");
        two.setPrice(new BigDecimal("123546.98").setScale(BigDecimal.ROUND_HALF_UP, 2) );
        two.setTotal(89L);
        two.setType("1");

        Fruit three = new Fruit();
        three.setName("苹果三级");
        three.setSid("3");
        three.setPrice(new BigDecimal("987.98").setScale(BigDecimal.ROUND_HALF_UP, 2) );
        three.setTotal(1039L);
        three.setType("1");

        Fruit four = new Fruit();
        four.setName("梨子一级");
        four.setSid("4");
        four.setPrice(new BigDecimal("97.98").setScale(BigDecimal.ROUND_HALF_UP, 2) );
        four.setTotal(39L);
        four.setType("2");

        Fruit five = new Fruit();
        five.setName("梨子二级");
        five.setSid("5");
        five.setPrice(new BigDecimal("970.98").setScale(BigDecimal.ROUND_HALF_UP, 2) );
        five.setTotal(399L);
        five.setType("2");

        Fruit six = new Fruit();
        six.setName("西瓜一级");
        six.setSid("6");
        six.setPrice(new BigDecimal("1970.98").setScale(BigDecimal.ROUND_HALF_UP, 2) );
        six.setTotal(2399L);
        six.setType("3");

        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);
        list.add(five);
        list.add(six);
        return list;
    }

    public static void main(String[] args) {
        int a = 25;
        double b = 31.11;
        System.out.println(a-b);
        // TODO Auto-generated method stub
        List<Fruit> list = initDate();

        //分组
        Map<String,List<Fruit>> map = list.stream().collect(Collectors.groupingBy(Fruit::getType));


        //分组求和
        Map<String, LongSummaryStatistics> collect = list.stream().collect(
                Collectors.groupingBy(Fruit::getType,
                        Collectors.summarizingLong(Fruit::getTotal)));
        for (Map.Entry<String, LongSummaryStatistics> entry : collect.entrySet()) {
            LongSummaryStatistics longSummaryStatistics = entry.getValue();
            System.out.println("----------------key----------------" + entry.getKey());
            System.out.println("求和:" + longSummaryStatistics.getSum());
            System.out.println("求平均" + longSummaryStatistics.getAverage());
            System.out.println("求最大:" + longSummaryStatistics.getMax());
            System.out.println("求最小:" + longSummaryStatistics.getMin());
            System.out.println("求总数:" + longSummaryStatistics.getCount());
        }


    }

    static class Fruit{
        private String sid;
        private String name;
        private String type;
        private Long total;
        private BigDecimal price;
        public String getSid() {
            return sid;
        }
        public void setSid(String sid) {
            this.sid = sid;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public Long getTotal() {
            return total;
        }
        public void setTotal(Long total) {
            this.total = total;
        }
        public BigDecimal getPrice() {
            return price;
        }
        public void setPrice(BigDecimal price) {
            this.price = price;
        }



    }

}