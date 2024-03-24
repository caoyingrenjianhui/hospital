package com.example.service.impl;

import com.example.controller.Code;
import com.example.controller.Result;
import com.example.domain.Comment;
import com.example.dao.CommentDao;
import com.example.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 尹洪运
 * @since 2024-02-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements ICommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Result add(Comment comment) {
        Map<String, Object> map = ThreadLocalUtil.get();
        comment.setUserID((String) map.get("userID"));
        comment.setTimestamp(LocalDate.now().toString());
        int insert = commentDao.insert(comment);
        if (insert == 0) {
            return new Result(null, Code.SAVE_ERR, "新增失败");
        }
        return new Result(comment, Code.SAVE_OK, "新增成功");
    }

    @Override
    public Result selectBuUserID() {
        Map<String, Object> map = ThreadLocalUtil.get();
        List<Comment> comments = commentDao.selectBuUserID((String) map.get("userID"));
        return new Result(comments, Code.GET_OK, "查询成功");
    }

    @Override
    public Result deleteById(Integer id) {
        int i = commentDao.deleteById(id);
        if (i == 0) {
            return new Result(null, Code.DELETE_ERR, "删除失败");
        }
        return new Result(null, Code.DELETE_OK, "删除成功");
    }
}
