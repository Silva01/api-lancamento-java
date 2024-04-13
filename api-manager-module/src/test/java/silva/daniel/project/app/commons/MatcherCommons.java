package silva.daniel.project.app.commons;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MatcherCommons {

    public static class DuplicateMatcher extends TypeSafeMatcher<Iterable<Integer>> {

        public static DuplicateMatcher hasNotDuplicate() {
            return new DuplicateMatcher();
        }

        @Override
        public boolean matchesSafely(Iterable<Integer> numbers) {
            return verifyRepeatedNumbers(groupByNumbers(numbers));
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("no duplicate numbers");
        }

        private Set<Map.Entry<Integer, Long>> groupByNumbers(Iterable<Integer> numbers) {
            return StreamSupport.stream(numbers.spliterator(), false)
                    .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                    .entrySet();
        }

        private boolean verifyRepeatedNumbers(Set<Map.Entry<Integer, Long>> entrySet) {
            return entrySet.stream().filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey).findAny().isEmpty();
        }
    }

    public static class ActiveDataMatcher extends TypeSafeMatcher<Iterable<Boolean>> {

        public static ActiveDataMatcher hasOneOrZeroDataActived() {
            return new ActiveDataMatcher();
        }

        @Override
        public boolean matchesSafely(Iterable<Boolean> numbers) {
            return countRegisterActived(numbers) <= 1;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("No has 2 register actived");
        }

        private Long countRegisterActived(Iterable<Boolean> numbers) {
            return StreamSupport.stream(numbers.spliterator(), false)
                    .filter(entry -> entry)
                    .count();
        }
    }


}
