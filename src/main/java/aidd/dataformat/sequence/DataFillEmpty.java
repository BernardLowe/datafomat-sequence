package aidd.dataformat.sequence;


import aidd.dataformat.sequence.annotation.ModelKey ;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 处理数据，使数据连续
 *
 * @author caijiacheng
 * @create 2020/7/10
 */
public class DataFillEmpty {

  /**
   * 处理数据，使数据连续
   *
   * @param data        要处理的数据
   * @param constructor 类的构造方法
   * @param setConsumer 属性的getter方法
   * @param getFunction 属性的setter方法
   * @param sequence    序号生成器
   * @return
   */
  public static <T, U extends Comparable<? super U>> List handle(
      List<T> data, Supplier<T> constructor,
      BiConsumer<T, U> setConsumer, Function<T, U> getFunction, Sequence sequence) {
    Set seqList = data.stream().map(getFunction).collect(Collectors.toSet());
    for (Object seq : sequence.getIndexs()) {
      if (!seqList.contains(seq)) {
        T t = constructor.get();
        setConsumer.accept(t, (U) seq);
        data.add(t);
      }
    }
    data = data.stream()
        .sorted(Comparator.comparing(getFunction))
        .collect(Collectors.toList());
    data = data.stream().sorted(Comparator.comparing(d -> {
      if (sequence instanceof IntSequence) {
        return (U) new Integer(getFunction.apply(d).toString());
      }
      return getFunction.apply(d);
    })).collect(Collectors.toList());
    return data;
  }

  /**
   * 处理数据，使数据连续，需给model类的连续属性加@ModelKey注解
   *
   * @param data     要处理的数据
   * @param clazz    类对象
   * @param sequence 序号生成器
   * @return List
   */
  public static <T, U extends Comparable<? super U>> List handle(
      List<T> data, Class<T> clazz, Sequence sequence) {
    try {
      Field keyField = getKeyField(clazz);
      if (keyField != null) {
        Set seqList = data.stream().map(d -> invokeGetter(d, keyField))
            .collect(Collectors.toSet());
        for (Object seq : sequence.getIndexs()) {
          if (!seqList.contains(seq)) {
            T t = clazz.newInstance();
            invokeSetter(t, keyField, seq);
            data.add(t);
          }
        }
        data = data.stream().sorted(Comparator.comparing(d -> {
          if (sequence instanceof IntSequence) {
            return (U) new Integer(invokeGetter(d, keyField).toString());
          }
          return (U) invokeGetter(d, keyField);
        })).collect(Collectors.toList());
      }
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return data;
  }

  private static Field getKeyField(Class clazz) {
    Field[] fields = clazz.getDeclaredFields();
    Field keyField = null;
    for (Field field : fields) {
      if (field.isAnnotationPresent(ModelKey.class)) {
        keyField = field;
        break;
      }
    }
    return keyField;
  }

  private static <T> Object invokeGetter(T t, Field field) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("get").append(field.getName().substring(0, 1).toUpperCase())
          .append(field.getName().substring(1));
      Method method = t.getClass().getMethod(sb.toString());
      return method.invoke(t);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static <T> void invokeSetter(T t, Field field, Object value) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("set").append(field.getName().substring(0, 1).toUpperCase())
          .append(field.getName().substring(1));
      Method method = t.getClass().getMethod(sb.toString(), field.getType());
      method.invoke(t, new Object[]{value});
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
