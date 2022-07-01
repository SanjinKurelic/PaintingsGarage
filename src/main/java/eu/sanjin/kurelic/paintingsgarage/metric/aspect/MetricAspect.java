package eu.sanjin.kurelic.paintingsgarage.metric.aspect;

import eu.sanjin.kurelic.paintingsgarage.metric.mapper.ImageFilterMapper;
import eu.sanjin.kurelic.paintingsgarage.metric.model.TimerMetricName;
import eu.sanjin.kurelic.paintingsgarage.photo.filter.ImageFilterType;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MetricAspect {

  private final MeterRegistry meterRegistry;

  private final ImageFilterMapper mapper;

  @PostConstruct
  public void init() {
    Arrays.stream(TimerMetricName.values()).forEach(timerMetricName -> Timer.builder(timerMetricName.name().toLowerCase(Locale.ROOT))
      .description(timerMetricName.getDescription())
      .register(meterRegistry));
  }

  @Around("@annotation(eu.sanjin.kurelic.paintingsgarage.metric.aspect.TimerMetric)")
  public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
    var startTime = System.nanoTime();

    var result = joinPoint.proceed();

    Arrays.stream(joinPoint.getArgs())
      .filter(ImageFilterType.class::isInstance)
      .findAny()
      .ifPresent(o -> Metrics
        .timer(mapper.mapImageFilterTypeToTimerMetricName((ImageFilterType) o).name().toLowerCase(Locale.ROOT))
        .record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS)
      );

    return result;
  }
}
