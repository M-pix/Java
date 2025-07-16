public class Statistic {

    public void BriefStatistic() {
        System.out.println("Amount of int: " + amountLine_[0]);
        System.out.println("Amount of float: " + amountLine_[1]);
        System.out.println("Amount of str: " + amountLine_[2]);
    }

    public void FullStatistic() {
        System.out.println("min int: " + statisticInt_[0]);
        System.out.println("max int: " + statisticInt_[1]);
        System.out.println("sum int: " + statisticInt_[2]);
        System.out.println("average int: " + average_[0]);
        System.out.println("min float: " + statisticFloat_[0]);
        System.out.println("max float: " + statisticFloat_[1]);
        System.out.println("sum float: " + statisticFloat_[2]);
        System.out.println("average float: " + average_[1]);
        System.out.println("min str: " +  statisticStr_ [0]);
        System.out.println("max str: " +  statisticStr_ [1]);
    }
    public int[] statisticInt_ = {0, 0, 0};
    public float[] statisticFloat_ = {0, 0, 0};
    public int[] statisticStr_ = {0, 0};
    public int[] amountLine_ = {0, 0, 0};
    
    public double average_[] = {0,0};
}
