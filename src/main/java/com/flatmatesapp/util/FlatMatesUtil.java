package com.flatmatesapp.util;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author iulian.dafinoiu
 */
@Slf4j
public class FlatMatesUtil {

    public static <T> Optional<T> getFirstObjectFromList(List<T> interestingList) {
        T firstObject = null;
        if (interestingList == null || interestingList.isEmpty()) {
            log.warn("The interesting list is empty");
            return Optional.empty();
        }
        if (interestingList.size() > 0) {
            if (interestingList.size() > 1) {
                log.warn("Multiple occurences found in interesting list of " + interestingList.get(0).getClass());
            }
            firstObject = interestingList.get(0);
        }
        return Optional.of(firstObject);
    }
}
