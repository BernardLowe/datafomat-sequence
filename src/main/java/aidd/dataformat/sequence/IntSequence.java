package aidd.dataformat.sequence;

/**
 * 数字序号生成器，如:0,1,2,...,10 new IntSequence(0, 10)
 *
 * @author caijiacheng
 * @create 2020/7/10
 */
public class IntSequence extends Sequence<Integer> {

  public IntSequence(int strat, int end) {
    super(strat, end);
  }

  @Override
  public void create() {
    for (int i = this.getStart(); i <= this.getEnd(); i++) {
      this.add(String.valueOf(i));
    }
  }
}
