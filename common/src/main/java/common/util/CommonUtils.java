package common.util;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/14.
 */
public class CommonUtils {


    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return  MD5加密后的字符串
     */
    public static String getMD5(String str) {
        if(StringUtils.isEmpty(str)){return null;}
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String formatDate(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String newDate = format.format(date);
        return newDate;
    }

    public static Long getLongDate(String date){
        Long dateTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = (Date)sdf.parse(date);
            dateTime = date1.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime ;
    }

    public static String getStrDate(Long date){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(date * 1000);
        String str = sdf.format(date1);
        return str;
    }

    public static int getFirstResult(int page){
        int firstResult = 0;
        if(page > 0){
            firstResult = (page - 1) * 10;
        }
        return firstResult;
    }

    public static String getResultStr(String str){
        if(StringUtils.isNotEmpty(str)){
            if(str.length() > 3){
                StringBuffer sb = new StringBuffer(str);
                for(int i = str.length()-3; i > 0; i=i-3){
                    sb.insert(i, ","); //sb.insert (1, "**");
                }
                return sb.toString();
            }else{
                return str;
            }
        }
        return null;
    }

    public static String cleanHtml(String html){
        if(StringUtils.isNotEmpty(html)){
            return Jsoup.clean(html, Whitelist.none());
        }
        return "";
    }

    public static String cleanHtml2(String html){
        if(StringUtils.isNotEmpty(html)){
            Whitelist whitelist = new Whitelist();
            whitelist.addTags("em");
            return Jsoup.clean(html, whitelist);
        }
        return "";
    }

    public static String filterEmpty(Object obj){
        if(obj != null){
            String str = obj.toString();
            str = str.replaceAll(" ", "");
            str = str.replaceAll("　", "");
            str = str.replaceAll("&nbsp;", "");
            return str;
        }else{
            return "";
        }
    }

    public static String escapeQueryChars(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'
                    || Character.isWhitespace(c)) {
            }else {

                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String zyQueryChars(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'
                    || Character.isWhitespace(c)) {
                sb.append("\\");
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String getStrDate(Integer min){
        return getDate(min).getTime()/1000+"";
    }

    public static Date getDate(Integer min){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -min);
        Date date = c.getTime();
        return date;
    }

    public static String format(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String newDate = format.format(date);
        return newDate;
    }

    public static String format(Long value,String pattern){
        if (value != null){
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(Long.parseLong(value.toString())*1000);
            SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
            return dateformat.format(c.getTime());
        }
        return "";
    }


    public static String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean notEmpty(String str){
        if(str != null && !str.replaceAll(" ","").equals("")){
            return true;
        }else{
            return false;
        }
    }

    public static String getImgStr(String imgStrs,String pre){
        String img = "";
        if (StringUtils.isNotBlank(imgStrs)){
            String[] strs = imgStrs.split(",");
            if (strs.length >0 ){
                for(int i=0;i<strs.length;i++){
                    String url = pre+"/"+strs[i];
                    if (i == strs.length-1){
                        img += url;
                    }else {
                        img += url+",";
                    }
                }
            }
        }
        return img;
    }

    public static String replaceStatus(String str){
        if (StringUtils.isNotBlank(str)){
            str = str.replaceAll("\\[","").replaceAll("\\]","")
                    .replaceAll("0","待初审").replaceAll("1","待复审").replaceAll("2","初审未通过")
                    .replaceAll("3","待核实").replaceAll("4","复审未通过").replaceAll("5","待整改")
                    .replaceAll("6","信息不准确").replaceAll("7","已整改");
            return str;
        }
        return "";
    }
}
