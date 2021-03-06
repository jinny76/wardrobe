package jbolt.android.wardrobe.service;

import java.io.File;
import java.util.List;
import jbolt.android.wardrobe.service.po.Person;
import jbolt.android.wardrobe.service.po.PersonMessages;
import jbolt.framework.crud.GenericCrudService;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;
import jbolt.platform.common.biz.exception.BizAppException;
import jbolt.platform.common.biz.exception.BizRuntimeException;

/**
 * <p>Title: PersonManager</p>
 * <p>Description: PersonManager</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public interface PersonManager extends GenericCrudService<Person> {

    /**
     * Create person with pictures
     *
     * @param person Person
     * @param pics   Pictures
     * @return Collocation with id
     * @throws CrudApplicationException #
     * @throws CrudRuntimeException     #
     */
    Person createWithPics(Person person, File[] pics) throws CrudApplicationException, CrudRuntimeException;


    /**
     * Add relations
     *
     * @param masterPersonId Person id
     * @param linkPersonId   Link person id
     * @param type           Type
     * @throws jbolt.framework.crud.exception.CrudApplicationException
     *          #
     * @throws jbolt.framework.crud.exception.CrudRuntimeException
     *          #
     */
    void addRelations(String masterPersonId, String linkPersonId, Integer type) throws CrudApplicationException, CrudRuntimeException;

    /**
     * Has relation
     *
     * @param masterPersonId Person id
     * @param linkPersonId   Line person id
     * @param type           Type
     * @return True/has
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    Boolean hasRelation(String masterPersonId, String linkPersonId, Integer type) throws BizAppException, BizRuntimeException;

    /**
     * Load relations
     *
     * @param masterPersonId Master person id
     * @param type           Type
     * @return Person list
     * @throws jbolt.platform.common.biz.exception.BizAppException
     *          #
     * @throws jbolt.platform.common.biz.exception.BizRuntimeException
     *          #
     */
    List<Person> loadRelations(String masterPersonId, Integer type) throws BizAppException, BizRuntimeException;

    /**
     * Send message
     *
     * @param messages Messages
     * @throws jbolt.framework.crud.exception.CrudApplicationException
     *          #
     * @throws jbolt.framework.crud.exception.CrudRuntimeException
     *          #
     */
    void sendMessage(PersonMessages messages) throws CrudApplicationException, CrudRuntimeException;


    /**
     * Load private message by id
     *
     * @param messageId Message id
     * @return Message
     * @throws CrudApplicationException #
     * @throws CrudRuntimeException     #
     */
    PersonMessages loadPrivateMessage(String messageId) throws CrudApplicationException, CrudRuntimeException;

    /**
     * Load unread messages
     *
     * @param personId Person id
     * @return Person messages
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    List<PersonMessages> loadUnreadMessages(String personId) throws BizAppException, BizRuntimeException;

    /**
     * Load unread messages
     *
     * @param personId Person id
     * @param type     Type
     * @return Person messages
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    List<PersonMessages> loadMessagesByType(String personId, Integer type) throws BizAppException, BizRuntimeException;

    /**
     * Modify password
     *
     * @param personId Person id
     * @param newPwd   New password
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    void changePassword(String personId, String newPwd) throws BizAppException, BizRuntimeException;

    /**
     * Modify person with pictures
     *
     * @param person Person
     * @param pics   Pics
     * @throws CrudApplicationException #
     * @throws CrudRuntimeException     #
     */
    void modifyWithPics(Person person, File[] pics) throws CrudApplicationException, CrudRuntimeException;

    /**
     * Offence report
     *
     * @param personId Person id
     * @param msg      Message
     * @param reportBy Report by
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    void offenceReport(String personId, String msg, String reportBy) throws BizAppException, BizRuntimeException;

    /**
     * Return nick name
     *
     * @param personId Person id
     * @return Nick name
     * @throws CrudRuntimeException #
     */
    String getNickName(String personId) throws CrudRuntimeException;

    /**
     * Change portraits
     *
     * @param userId User id
     * @param pics   Pictures
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    void changePortrait(String userId, File[] pics) throws BizAppException, BizRuntimeException;

    /**
     * Delete relations
     *
     * @param personId     Person id
     * @param linkId       Link id
     * @param relationType Relation type
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    void deleteRelations(String personId, String linkId, Integer relationType) throws BizAppException, BizRuntimeException;

}
