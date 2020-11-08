package com.siliver.ch1.rest.ch11;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.siliver.ch1.entity.Baike;
import com.siliver.ch1.entity.Comment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DbCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
public class DriverTestCrontroller {

    Log log= LogFactory.getLog(this.getClass());

    @Autowired
    private MongoTemplate template;

    @GetMapping("/baike/{name}")
    public Baike findUser(@PathVariable String name){
        final String id=name;
        Baike baike=template.execute(new DbCallback<Baike>() {
            @Override
            public Baike doInDB(MongoDatabase db) throws MongoException, DataAccessException {
                MongoCollection<Document> collection=db.getCollection("baike");
                MongoCursor<Document> cursor=collection.find(new Document("_id",id)).iterator();

                //-------------
                //进行节点遍历获取
                try {
                    while (cursor.hasNext()){
                        log.info(cursor.next().toJson());
                    }
                }finally {
                    cursor.close();
                }
                return null;
                //----------


                //----------
                //进行第一个节点信息查询，非遍历器，各个属性查询
                /*Document doc=collection.find(new Document("_id",id)).first();
                log.info(doc.toJson());
                Baike baike1=new Baike();
                baike1.setDesc(doc.getString("desc"));
                Comment comment=new Comment();
                Document doc2=doc.get("comment",Document.class);
                comment.setBad(doc2.getInteger("bad"));
                comment.setGood(doc2.getInteger("good"));
                baike1.setComment(comment);
                return baike1;*/
                //----------

            }
        });
        return baike;
    }
}
