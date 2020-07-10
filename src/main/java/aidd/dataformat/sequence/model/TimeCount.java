package aidd.dataformat.sequence.model;
import aidd.dataformat.sequence.annotation.ModelKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈〉
 *
 * @author caijiacheng
 * @create 2020/7/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeCount {
  @ModelKey
  private String time;

  private Integer count = 0;
}
