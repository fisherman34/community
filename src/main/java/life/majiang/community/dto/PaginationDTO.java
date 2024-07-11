package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 对Index页面输入页码后可以返回相应分页，除了访问的页面以外，还需记录前后相连的页面。
 * 前后相连页的信息就记录在 PaginationDTO 中
 * @author Sam
 * @create 2024-05-30 10:25 PM
 */

@Data
public class PaginationDTO<T> {
  private List<T> data;
  private boolean showPrevious;  //显示向前按钮
  private boolean showFirstPage;  //显示首页按钮
  private boolean showNext;  //显示下一页页按钮
  private boolean showEndPage;  //显示尾页按钮
  private Integer page;  //当前页页码
  private List<Integer> pages = new ArrayList<>();  //储存前后连续的相关页面
  private Integer totalPage; //一共显示多少页

  public void setPagination(Integer totalPage, Integer page) {  //totalCount表中记录的行数

    this.totalPage = totalPage;

    this.page = page;
    pages.add(page);  //在page中加入当前页码
    for (int i = 1; i <= 3; i++) {
      if (page - i > 0) {  //补全page的前三页，若计算出的页码小于0则不加入
        pages.add(0, page - i);  //往前循环是每次都加到第0个位置
      }
      if (page + i <= totalPage) {
        pages.add(page + i);
      }
    }

    if (page == 1){  //是否展示上一页
      showPrevious = false;
    } else {
      showPrevious = true;
    }

    if(page == totalPage) {  //是否展示下一页
      showNext = false;
    } else {
      showNext = true;
    }
    // 是否显示第一页
    if(pages.contains(1)){
      showFirstPage = false;
    } else {
      showFirstPage = true;
    }

    if(pages.contains(totalPage)) {
      showEndPage = false;
    } else {
      showEndPage = true;
    }
  }
}
