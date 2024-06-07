package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** 把QuestionMapper与UserMapper数据相连接的中间层
 * QuestionDTO是Question的wrapper, 因为Question的fields对应了数据库里的每一列,
 * 不能改动Question的结构，使用QuestionDTO来warp Question并增加user这个field。
 * 这个把QuestionMapper与UserMapper两种数据相连接的操作由QuestionService来实现
 * @author Sam
 * @create 2024-05-30 6:44 PM
 */

@Service
public class QuestionService {

  @Autowired
  private QuestionMapper questionMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private QuestionExtMapper questionExtMapper;

  /**
   * list()方法生成的 List<QuestionDTO> 是对List<Question> questions的wrapper,
   * QuestionDTO比Question多一个user的field
   */
  public PaginationDTO list(Integer page, Integer size) {

    PaginationDTO paginationDTO = new PaginationDTO();
    Integer totalPage; //一共显示多少页

    Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());  //获得表中记录的行数

    if (totalCount % size == 0) {
      totalPage = totalCount / size;
    } else {
      totalPage = totalCount / size + 1;
    }

    if (page < 1) {  //防止页码误输入，增加容错性
      page = 1;
    } else if (page > totalPage  && page > 1) {
      page = totalPage;
    }

    paginationDTO.setPagination(totalPage, page);
    // size*(page - 1) 计算公式
    Integer offset = size * (page -1);
    List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
    List<QuestionDTO> questionDTOList = new ArrayList<>();

    for (Question question : questions) {
      User user = userMapper.selectByPrimaryKey(question.getCreator());
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(question, questionDTO);
      questionDTO.setUser(user);
      questionDTOList.add(questionDTO);
    }
    paginationDTO.setQuestions(questionDTOList);


    return paginationDTO;
  }

  public PaginationDTO list(Long userId, Integer page, Integer size) {
    PaginationDTO paginationDTO = new PaginationDTO();

    Integer totalPage; //一共显示多少页

    QuestionExample questionExample = new QuestionExample(); //获得表中记录的行数
    questionExample.createCriteria()
            .andCreatorEqualTo(userId);
    Integer totalCount = (int) questionMapper.countByExample(questionExample);

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
    QuestionExample example = new QuestionExample();
    example.createCriteria()
            .andCreatorEqualTo(userId);
    List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

    List<QuestionDTO> questionDTOList = new ArrayList<>();

    for (Question question : questions) {
      User user = userMapper.selectByPrimaryKey(question.getCreator());
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(question, questionDTO);
      questionDTO.setUser(user);
      questionDTOList.add(questionDTO);
    }
    paginationDTO.setQuestions(questionDTOList);


    return paginationDTO;


  }

  public QuestionDTO getById(Long id) {
    Question question = questionMapper.selectByPrimaryKey(id);
    if(question == null){
      throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
    }
    QuestionDTO questionDTO = new QuestionDTO();
    BeanUtils.copyProperties(question, questionDTO);
    User user = userMapper.selectByPrimaryKey(question.getCreator());
    questionDTO.setUser(user);
    return questionDTO;
  }

  public void createOrUpdate(Question question) {
    if (question.getId() == null){
      // 创建新问题
      question.setGmtCreate(System.currentTimeMillis());
      question.setGmtModified(question.getGmtCreate());
      question.setViewCount(0);
      question.setLikeCount(0);
      question.setCommentCount(0);
      questionMapper.insert(question);
    } else {
      // 更新问题
      Question updateQuestion = new Question();
      updateQuestion.setGmtModified(System.currentTimeMillis());
      updateQuestion.setTitle(question.getTitle());
      updateQuestion.setDescription(question.getDescription());
      updateQuestion.setTag(question.getTag());
      QuestionExample example = new QuestionExample();
      example.createCriteria()
              .andIdEqualTo(question.getId());
      int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
      if(updated != 1){  //说明服务其中该ID不存在
        throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
      }
    }
  }

  public void incView(Long id) {
    Question question = new Question();
    question.setId(id);
    question.setViewCount(1);
    questionExtMapper.incView(question);
  }
}
