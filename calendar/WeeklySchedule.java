package calendar;

import java.util.Arrays;
import java.util.Calendar;

public class WeeklySchedule {
    static public Object[][] getSchedule(int year, int month) {
        //1.静态方法getSchedule，通过输入年和月份返回一个数组，里面存储了日期从1到月底。
        // 创建一个6行7列的Object数组
        Object[][] schedule = new Object[6][7];

        // 获取指定月份的第一天是星期几以及该月份的总天数
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 从0开始，0代表星期日
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 初始化数组
        for (Object[] objects : schedule) {
            Arrays.fill(objects, null);
        }

        // 填充日历
        int day = 1;
        for (int row = 0; row < schedule.length; row++) {
            for (int col = 0; col < schedule[row].length; col++) {
                // 第一个星期的列数小于firstDayOfWeek的单元格为空
                if (row == 0 && col < firstDayOfWeek) {
                    continue;
                }
                if (day <= daysInMonth) {
                    schedule[row][col] = day++;
                }
            }
        }

        return schedule;
    }
}
