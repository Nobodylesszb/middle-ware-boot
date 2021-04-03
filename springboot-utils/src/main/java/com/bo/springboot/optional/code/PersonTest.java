package com.bo.springboot.optional.code;

import org.apache.commons.lang.StringUtils;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther: bo
 * @Date: 2021/4/3 10:12
 * @version:
 * @description:
 */
public class PersonTest {

    /**
     * 传统写法
     *
     * @param person
     * @return
     */
    public static String inWhichCityLowercaseTU(Person person) {
        if (null != person) {
            Location location = person.getLocation();
            if (null != location) {
                String city = location.getCity();
                if (StringUtils.isNotBlank(city)) {
                    return city.toLowerCase();

                } else {
                    return "nowhere";
                }
            } else {
                return "nowhere";
            }
        } else {
            return "nowhere";
        }
    }

    /**
     * 传统写法二
     *
     * @param person
     * @return
     */
    public static String inWhichCityLowercaseT(Person person) {
        if (person != null && person.getLocation() != null && person.getLocation().getCity() != null) {
            return person.getLocation().getCity();
        }
        return "nowhere";
    }

    /**
     * 功能描述：判断Person在哪个城市，并返回城市小写名；失败时返回nowhere。
     *
     * @param person
     * @return
     */
    public static String inWhichCityLowercase(final Person person) {
        return Optional.ofNullable(person)
                .map(Person::getLocation)
                .map(Location::getCity)
                .map(String::toLowerCase)
                .orElse("nowhere");

    }

    /**
     * 功能描述：判断Person在哪个国家，并返回国家大写名；失败时抛出异常。
     *
     * @param person
     * @return
     */
    public static String inWhichCountryUppercase(final Person person) {
        return Optional.ofNullable(person)
                .map(Person::getLocation)
                .map(Location::getCountry)
                .map(String::toLowerCase)
//                .orElseThrow(()-> new NoSuchElementException())
                .orElseThrow(NoSuchElementException::new);

    }

    private String fromCountry(final String country) {
        return "from " + country;
    }

    private static String fromNowhere() {
        return "from Nowhere";
    }

    /**
     * 功能描述：判断Person在哪个国家，并返回from + 国家名；失败时返回from Nowhere。
     *
     * @param person 参数
     * @return 返回一个字符串
     */
    private String fromWhere(final Person person) {
        return Optional.ofNullable(person)
                .map(Person::getLocation)
                .map(Location::getCountry)
                .map(this::fromCountry)
                .orElseGet(PersonTest::fromNowhere);
    }

    /**
     * 功能描述：当Person在中国时，调用Location.introduce()；否则什么都不做。
     * @param person 参数
     */
    private static void introduceChineseT(Person person) {
        if (person != null
                && person.getLocation() != null
                && person.getLocation().getCountry() != null
                && "China".equals(person.getLocation().getCountry())) {
            Location.introduce("China");
        }
    }

    public static void introduceChinese(Person person){
        Optional.ofNullable(person)
//                .map(person1 -> person1.getLocation())
                .map(Person::getLocation)
                .map(Location::getCountry)
//                .filter(s -> s.equals("china"))
                .filter("China"::equals)
                .ifPresent(Location::introduce);
    }


    public String makeDevDetailVersion(final String strDevVersion, final String strDevDescr, final String strDevPlatformName)
    {
        String detailVer = "VRP";

        if (null != strDevPlatformName && !strDevPlatformName.isEmpty())
        {
            detailVer = strDevPlatformName;
        }

        String versionStr = null;
        Pattern verStrPattern =null;
        verStrPattern = Pattern.compile("Version(\\s)*([\\d]+[\\.][\\d]+)");
        if(strDevDescr != null)
        {
            Matcher verStrMatcher = verStrPattern.matcher(strDevDescr);
            if (verStrMatcher.find())
            {
                versionStr = verStrMatcher.group();
            }
            if (null != versionStr)
            {
                Pattern digitalPattern = Pattern.compile("([\\d]+[\\.][\\d]+)");
                Matcher digitalMatcher = digitalPattern.matcher(versionStr);
                if (digitalMatcher.find())
                {
                    detailVer = detailVer + digitalMatcher.group() + " ";
                }
            }
        }

        return detailVer + strDevVersion;
    }



    private static final Pattern VRP_VER = Pattern.compile("Version\\s+(\\d\\.\\d{3})\\s+");
    private static String makeDetailedDevVersion(final String strDevVersion, final String strDevDescr, final String strDevPlatformName) {
        String detailVer = Optional.ofNullable(strDevPlatformName)
                .filter(s -> !s.isEmpty()).orElse("VRP");

        return detailVer + Optional.ofNullable(strDevDescr)
                .map(VRP_VER::matcher)
                .filter(matcher -> matcher.find())
//                .filter(Matcher::find)
                .map(m -> m.group(1))
                .map(v -> v + " ").orElse("")
                + strDevVersion;
    }
}
