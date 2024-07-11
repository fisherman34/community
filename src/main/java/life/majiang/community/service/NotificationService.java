package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Sam
 * @create 2024-07-04 7:47 PM
 */

@Service
public class NotificationService {

  @Autowired
  private NotificationMapper notificationMapper;

  @Autowired
  private UserMapper userMapper;

  public PaginationDTO list(Long userId, Integer page, Integer size) {
    PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

    Integer totalPage; //一共显示多少页

    NotificationExample notificationExample = new NotificationExample(); //获得表中记录的行数
    notificationExample.createCriteria()
            .andReceiverEqualTo(userId);
    Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

    if (totalCount % size == 0) {
      totalPage = totalCount / size;
    } else {
      totalPage = totalCount / size + 1;
    }

    if (page < 1) {  //防止页码误输入，增加容错性
      page = 1;
    } else if (page > totalPage) {
      page = totalPage;
    }

    paginationDTO.setPagination(totalPage, page);

    // size*(page - 1) 计算公式
    Integer offset = size * (page - 1);
    NotificationExample example = new NotificationExample();
    example.createCriteria()
            .andReceiverEqualTo(userId);
    example.setOrderByClause("gmt_create desc");
    List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

    if (notifications.size() == 0) {
      return paginationDTO;
    }

    List<NotificationDTO> notificationDTOS = new ArrayList<>();

    for (Notification notification: notifications) {
      NotificationDTO notificationDTO = new NotificationDTO();
      BeanUtils.copyProperties(notification, notificationDTO);
      notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
      notificationDTOS.add(notificationDTO);
    }

    paginationDTO.setData(notificationDTOS);
    return paginationDTO;
  }

  public Long unreadCount(Long userId) {
    NotificationExample notificationExample = new NotificationExample();
    notificationExample.createCriteria()
            .andReceiverEqualTo(userId)
            .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
    return notificationMapper.countByExample(notificationExample);
  }

  public NotificationDTO read(Long id, User user) {
    Notification notification = notificationMapper.selectByPrimaryKey(id);
    if(notification == null) {
      throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
    }
    if (!Objects.equals(notification.getReceiver(), user.getId())) {
      throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
    }

    notification.setStatus(NotificationStatusEnum.READ.getStatus());
    notificationMapper.updateByPrimaryKey(notification);

    NotificationDTO notificationDTO = new NotificationDTO();
    BeanUtils.copyProperties(notification, notificationDTO);
    notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
    return notificationDTO;
  }
}
