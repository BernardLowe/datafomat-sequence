package aidd.dataformat.sequence.test;

import aidd.dataformat.sequence.DataFillEmpty;
import aidd.dataformat.sequence.DateSequence;
import aidd.dataformat.sequence.model.TimeCount;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * 〈〉
 *
 * @author caijiacheng
 * @create 2020/7/10
 */
public class SequenceTest {

  /**
   * 根据开始时间查多少天
   */
  @Test
  public void test1() {
    List<TimeCount> list = new ArrayList<>();
    // 获取10天前时间
    LocalDateTime startTime = LocalDateTime.of(
        LocalDate.now(ZoneId.systemDefault()).plusDays(-10), LocalTime.MIN);
    List<TimeCount> result = DataFillEmpty
        .handle(list, TimeCount.class, new DateSequence(startTime.toLocalDate(), 10));
    result.forEach(item -> {
      System.out.println(item.getTime() + " ---- " + item.getCount());
    });
  }

  /**
   * 根据开始时间和结束时间
   */
  @Test
  public void test2() {
    List<TimeCount> list = new ArrayList<>();
    // 获取10天前时间
    LocalDateTime startTime = LocalDateTime.of(
        LocalDate.now(ZoneId.systemDefault()).plusDays(-10), LocalTime.MIN);
    // 获取今天时间
    LocalDateTime endTime = LocalDateTime.of(
        LocalDate.now(ZoneId.systemDefault()).plusDays(0), LocalTime.MIN);
    List<TimeCount> result = DataFillEmpty
        .handle(list, TimeCount.class, new DateSequence(startTime.toLocalDate(), endTime.toLocalDate()));
    result.forEach(item -> {
      System.out.println(item.getTime() + " ---- " + item.getCount());
    });
  }
}
