package cavani.endorfina.agency.model.persistence;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-27T13:53:12.612-0300")
@StaticMetamodel(Account.class)
public class Account_ {
	public static volatile SingularAttribute<Account, Long> id;
	public static volatile SingularAttribute<Account, String> name;
	public static volatile SingularAttribute<Account, String> principal;
	public static volatile SingularAttribute<Account, String> secret;
	public static volatile SingularAttribute<Account, Boolean> active;
	public static volatile SingularAttribute<Account, Date> createDate;
}
