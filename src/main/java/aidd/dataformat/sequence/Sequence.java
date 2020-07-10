package aidd.dataformat.sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * 序号生成器
 *
 * @author caijiacheng
 * @create 2020/7/10
 */
public abstract class Sequence<S> {

  private List indexs = new ArrayList<>();
  private S start;
  private S end;

  public abstract void create();

  public Sequence() {

  }

  public Sequence(S start, S end) {
    this.start = start;
    this.end = end;
    this.create();
  }

  public void add(Object index) {
    indexs.add(index);
  }

  public List getIndexs() {
    return indexs;
  }

  public void setIndexs(List indexs) {
    this.indexs = indexs;
  }

  public S getStart() {
    return start;
  }

  public void setStart(S start) {
    this.start = start;
  }

  public S getEnd() {
    return end;
  }

  public void setEnd(S end) {
    this.end = end;
  }
}
