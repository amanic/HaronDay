package com.haron.pro.dao.mapper;

import com.haron.pro.dao.entity.AnniversaryAlbum;
import com.haron.pro.dao.entity.AnniversaryAlbumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnniversaryAlbumMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    int countByExample(AnniversaryAlbumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    int deleteByExample(AnniversaryAlbumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    int insert(AnniversaryAlbum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    int insertSelective(AnniversaryAlbum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    List<AnniversaryAlbum> selectByExample(AnniversaryAlbumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    AnniversaryAlbum selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    int updateByExampleSelective(@Param("record") AnniversaryAlbum record, @Param("example") AnniversaryAlbumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    int updateByExample(@Param("record") AnniversaryAlbum record, @Param("example") AnniversaryAlbumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    int updateByPrimaryKeySelective(AnniversaryAlbum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table anniversary_album
     *
     * @mbggenerated Mon Aug 27 15:25:17 CST 2018
     */
    int updateByPrimaryKey(AnniversaryAlbum record);
}