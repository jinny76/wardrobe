package jbolt.android.wardrobe.service.impl;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import jbolt.android.wardrobe.PersonMessageType;
import jbolt.android.wardrobe.RelationsType;
import jbolt.android.wardrobe.service.CollocationManager;
import jbolt.android.wardrobe.service.ImageManager;
import jbolt.android.wardrobe.service.PersonManager;
import jbolt.android.wardrobe.service.ShowsType;
import jbolt.android.wardrobe.service.po.ArtifactItem;
import jbolt.android.wardrobe.service.po.Collocation;
import jbolt.android.wardrobe.service.po.CollocationComments;
import jbolt.android.wardrobe.service.po.Person;
import jbolt.android.wardrobe.service.po.PersonMessages;
import jbolt.android.webservice.servlet.LocalMethod;
import jbolt.core.dao.DAOExecutor;
import jbolt.core.dao.exception.DAOException;
import jbolt.core.dao.exception.PersistenceException;
import jbolt.core.dao.meta.JDBCQueryMeta;
import jbolt.core.numbering.NumberSystemManager;
import jbolt.core.numbering.exception.NumberGenerateException;
import jbolt.core.utilities.ObjectUtilities;
import jbolt.core.utilities.StringUtilities;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;
import jbolt.framework.crud.impl.GenericCrudDefaultService;
import jbolt.platform.common.biz.exception.BizAppException;
import jbolt.platform.common.biz.exception.BizRuntimeException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>Title: CollocationManagerDefaultImpl</p>
 * <p>Description: CollocationManagerDefaultImpl</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CollocationManagerDefaultImpl extends GenericCrudDefaultService<Collocation>
        implements CollocationManager {

    private NumberSystemManager uuidManager;
    private PersonManager personManager;
    private ImageManager imageManager;
    private DAOExecutor daoExecutor;

    public Collocation createWithPics(Collocation collocation, File[] pics) throws CrudApplicationException, CrudRuntimeException {
        collocation.setCreateDate(new Date());
        Collocation _collocation = create(collocation);
        imageManager.savePic(collocation.getId(), pics[0], true);
        imageManager.savePic(collocation.getId(), pics[1], false);
        return _collocation;
    }

    public void modifyWithPics(Collocation collocation, File[] pics) throws CrudApplicationException, CrudRuntimeException {
        collocation.getModifiedFields().add("*");
        try {
            persistenceManager.update(collocation);
            imageManager.savePic(collocation.getId(), pics[0], true);
            imageManager.savePic(collocation.getId(), pics[1], false);
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    @Override
    public void delete(Collocation domain) throws CrudApplicationException, CrudRuntimeException {
        super.delete(domain);
        imageManager.deletePic(domain.getId());
    }

    public String addComments(String collocationId, CollocationComments comments)
            throws CrudApplicationException, CrudRuntimeException {
        Collocation collocation = new Collocation();
        collocation.setId(collocationId);
        comments.setCollocation(collocation);
        try {
            String commentsId = (String) uuidManager.generateNumber(null, null, null, true);
            comments.setId(commentsId);
            persistenceManager.insert(comments);

            Collocation toUpdate = (Collocation) queryManager.find(collocation);
            Long commentsCounter = toUpdate.getCommentsCounter();
            if (commentsCounter == null) {
                commentsCounter = (long) 0;
            }
            commentsCounter += 1;
            toUpdate.setCommentsCounter(commentsCounter);
            toUpdate.getModifiedFields().add("commentsCounter");
            persistenceManager.update(toUpdate);
            PersonMessages personMessages = new PersonMessages();
            personMessages.setType(PersonMessageType.COMMENTS);
            personMessages.setSendFrom(comments.getOwnerId());
            personMessages.setSendTo(collocation.getOwnerId());
            personMessages.setMsg(personManager.getNickName(comments.getOwnerId()) + WebUtils.getI18nValue("messages.comments"));
            personManager.sendMessage(personMessages);
            return commentsId;
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        } catch (NumberGenerateException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    public List<Collocation> loadMyShows(String personId) throws BizAppException, BizRuntimeException {
        String sql = "select * from collocation where show=1 and owner_id=? order by create_date desc";
        JDBCQueryMeta queryMeta = new JDBCQueryMeta();
        queryMeta.setSql(sql);
        queryMeta.setBeanClazz(Collocation.class);
        queryMeta.setParameters(new Object[]{personId});
        try {
            return (List<Collocation>) daoExecutor.executeQuery(queryMeta);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Collocation> loadCollocations(String personId) throws BizAppException, BizRuntimeException {
        Collocation criteria = new Collocation();
        criteria.setOwnerId(personId);
        try {
            return (List<Collocation>) queryManager.findByAnyCriteria(criteria);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Collocation> loadShows(Integer type, String personId) throws BizAppException, BizRuntimeException {
        String sql = "select * from collocation where show=1 and (illegal is null or illegal=0)";
        if (ShowsType.HOTTEST.equals(type)) {
            sql += " order by adore_counter desc, create_date desc";
        } else if (ShowsType.NEWEST.equals(type)) {
            sql += " order by create_date desc";
        } else if (ShowsType.ATTENTION.equals(type)) {
            List<Person> personList = personManager.loadRelations(personId, RelationsType.OBSERVERS);
            if (!CollectionUtils.isEmpty(personList)) {
                StringBuilder sb = new StringBuilder();
                for (Person person : personList) {
                    sb.append("'");
                    sb.append(person.getId());
                    sb.append("',");
                }
                sb.append("'");
                sb.append(personId);
                sb.append("'");
                sql += " and owner_id in (" + sb + ")";
                sql += " order by create_date desc";
            } else {
                sql += " owner_id='" + personId + "' order by create_date desc"; //todo:refactor with parameters
            }
        }
        JDBCQueryMeta queryMeta = new JDBCQueryMeta();
        queryMeta.setSql(sql);
        queryMeta.setBeanClazz(Collocation.class);
        try {
            return (List<Collocation>) daoExecutor.executeQuery(queryMeta);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    public void reportIllegalCollocation(String collocationId, String msg, String reportBy) throws BizAppException, BizRuntimeException {
        Collocation pk = new Collocation();
        pk.setId(collocationId);
        try {
            Collocation toUpdate = (Collocation) queryManager.find(pk);
            toUpdate.setReportMsg(msg);
            toUpdate.setIllegal(true);
            toUpdate.setReportBy(reportBy);
            persistenceManager.update(toUpdate);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Collocation findById(String id) throws CrudApplicationException, CrudRuntimeException {
        Collocation pk = new Collocation();
        pk.setId(id);
        Collocation res = super.find(pk);
        String itemIds = res.getArtifactItemIds();
        if (!StringUtils.isEmpty(itemIds)) {
            String[] ids = StringUtils.split(itemIds, "|");
            String idsStr = StringUtilities.combineString(ids, "','");
            String sql = "select * from artifact_item where id in ('" + idsStr + "')";
            JDBCQueryMeta queryMeta = new JDBCQueryMeta();
            queryMeta.setSql(sql);
            queryMeta.setBeanClazz(ArtifactItem.class);
            try {
                Collection<ArtifactItem> items = daoExecutor.executeQuery(queryMeta);
                res.setItems((List<ArtifactItem>) items);
            } catch (DAOException e) {
                tracer.logError(ObjectUtilities.printExceptionStack(e));
                throw new CrudRuntimeException(e);
            }
        }
        return res;
    }

    public void showCollocation(String collocationId) throws BizAppException, BizRuntimeException {
        Collocation pk = new Collocation();
        pk.setId(collocationId);
        try {
            Collocation toUpdate = (Collocation) queryManager.find(pk);
            toUpdate.setShow(true);
            persistenceManager.update(toUpdate);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<CollocationComments> loadComments(String collocationId) throws BizAppException, BizRuntimeException {
        String sql = "select a.*,b.nick from collocation_comments a inner join person b on a.owner_id=b.id and a.id=?";
        JDBCQueryMeta queryMeta = new JDBCQueryMeta();
        queryMeta.setSql(sql);
        queryMeta.setBeanClazz(CollocationComments.class);
        queryMeta.setParameters(new Object[]{collocationId});
        try {
            return (List<CollocationComments>) daoExecutor.executeQuery(queryMeta);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    public void setImageManager(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    @LocalMethod
    public void setUuidManager(NumberSystemManager uuidManager) {
        this.uuidManager = uuidManager;
    }

    @LocalMethod
    public void setDaoExecutor(DAOExecutor daoExecutor) {
        this.daoExecutor = daoExecutor;
    }
}
