package aidd.dataformat.sequence;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 日期序号生成器，如:2020-01-01,2020-01-02,...,2020-01-10
 *
 * @author caijiacheng
 * @create 2020/7/10
 */
public class DateSequence extends Sequence<LocalDate> {

  public static final DateTimeFormatter DTF_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private int days;

  public DateSequence(long startMilli, long endMilli) {
    LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startMilli),
        ZoneId.systemDefault());
    LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endMilli),
        ZoneId.systemDefault());
    this.setStart(startTime.toLocalDate());
    this.setEnd(endTime.toLocalDate());
    this.create();
  }

  public DateSequence(LocalDate start, LocalDate end) {
    this.setStart(start);
    this.setEnd(end);
    this.create();
  }

  public DateSequence(LocalDate start, int days) {
    this.setStart(start);
    this.days = days;
    this.create();
  }

  @Override
  public void create() {
    LocalDate currentDate = this.getStart();
    if (days > 0) {
      for (int i = 1; i <= days; i++) {
        this.add(currentDate.format(DTF_YYYY_MM_DD));
        currentDate = currentDate.plusDays(1);
      }
    } else {
      do {
        this.add(currentDate.format(DTF_YYYY_MM_DD));
        currentDate = currentDate.plusDays(1);
      } while (currentDate.isBefore(this.getEnd()));
    }
  }
}
