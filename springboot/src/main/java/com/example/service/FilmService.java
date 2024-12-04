package com.example.service;


import com.example.entity.Film;
import com.example.mapper.CommentMapper;
import com.example.mapper.FilmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Resource
    private FilmMapper filmMapper;
    @Resource
    private CommentMapper commentMapper;

    /**
     * 新增
     */
    public void add(Film film) {
        filmMapper.insert(film);
    }

    /**
     * 更新
     */
    public void update(Film film) {
        filmMapper.updateById(film);
    }
    /**
     * 删除
     */
    public void deleteById(Integer id) {
        filmMapper.deleteById(id);
    }
    /**
     * 查询所有
     */
    public List<Film> selectAll(Film film){
        return filmMapper.selectAll(film);
    }

    /**
     * 分页查询
     * @param pageNum 当前页码
     * @param pageSize 每页个数
     * @return 分页对象 包含数据和分页参数 total
     */
    public PageInfo<Film> selectPage(Film film,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Film> list = filmMapper.selectAll(film);
        for (Film f : list) {
            this.setScore(f);
        }
        return PageInfo.of(list);
    }


    public Film selectById(Integer id) {
        Film film = filmMapper.selectById(id);
        this.setScore(film);
        return film;
    }

    //随机推荐电影
    public List<Film> selectRecommend(Integer filmId) {
        List<Film> films = this.selectAll(null);
        //把当前详情页的电影排除推荐
        films = films.stream().filter(film -> !film.getId().equals(filmId)).collect(Collectors.toList());
        Collections.shuffle(films);//打乱电影排序
        List<Film> filmList = films.stream().limit(3).collect(Collectors.toList());
        for (Film film : filmList){
            this.setScore(film);
        }
        return filmList;
    }
    private void setScore(Film film){
        //查询有多少条评论
        int total = commentMapper.selectTotal(film.getId());
        film.setCommentNum(total);
        if(total == 0){
            film.setScore(0D);
        }else {
            //求电影的平均分
            double sum = commentMapper.selectSum(film.getId());
            BigDecimal score = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(total), 1, RoundingMode.HALF_UP);
            film.setScore(score.doubleValue());
        }
    }

    //排行前五的电影数据
    public List<Film> selectRanking() {
        List<Film> filmList = filmMapper.selectAll(null);
        for (Film film : filmList){
            setScore(film);
        }
        return filmList.stream().sorted((f1, f2) -> f2.getScore().compareTo(f1.getScore())).limit(5).toList();
    }
}
