package org.example.bean;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ServiceBean {
    private String code;
    private String name;
    private List<ThirdSub> subList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ThirdSub> getSubList() {
        return subList;
    }

    public void setSubList(List<ThirdSub> subList) {
        this.subList = subList;
    }

    public void addSub(@NotNull ThirdSub sub) {
        if (subList == null) subList = new ArrayList<>();
        if (subList.stream().noneMatch(s -> s.code.equals(sub.code))) {
            subList.add(sub);
        } else {
            Optional<ThirdSub> optional = subList.stream().filter(s -> s.code.equals(sub.code)).findAny();
            if (optional.isPresent()) {
                ThirdSub thirdSub = optional.get();
                thirdSub.name = sub.name;
            }
        }
    }

    public void addFourSub(@NotNull FourthSub sub) {
        if (subList != null) {
            Optional<ThirdSub> optional = subList.stream().filter(b -> sub.code.startsWith(b.code)).findAny();
            optional.ifPresent(thirdSub -> thirdSub.addSub(sub));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceBean that = (ServiceBean) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public static class ThirdSub {
        private String code;
        private String name;
        private List<FourthSub> subList;

        public String getCode() {
            return code;
        }

        public ThirdSub() {
        }

        public ThirdSub(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<FourthSub> getSubList() {
            return subList;
        }

        public void setSubList(List<FourthSub> subList) {
            this.subList = subList;
        }

        public void addSub(FourthSub sub) {
            if (subList == null) subList = new ArrayList<>();
            if (subList.stream().noneMatch(s -> s.code.equals(sub.code))) {
                subList.add(sub);
            } else {
                Optional<FourthSub> optional = subList.stream().filter(s -> s.code.equals(sub.code)).findAny();
                if (optional.isPresent()) {
                    FourthSub fourthSub = optional.get();
                    fourthSub.name = sub.name;
                }
            }
        }
    }

    public static class FourthSub {
        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public FourthSub() {
        }

        public FourthSub(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
