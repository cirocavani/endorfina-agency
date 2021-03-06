Endorfina Agency
================

Java EE 6 Web Application.

Currently, this is more a technology sampling. ;)

(GitHub)

https://github.com/cirocavani/endorfina-agency

(OpenShift)

https://agency-cavani.rhcloud.com/

Gears
-----

**Account Service**

* Sign Up: create an account (email for confirmation)
* Sign In: authenticate an account
* Recovery: email with password and activation link if required
* Activation: authenticate and activate an account
* Account: show an account (can be deleted as deactivated)

Featuring
---------

(Java EE 6)
* CDI 1.0
* EJB 3.1
* JPA 2.0
* JMS 1.1
* Java Mail 1.4
* JSF 2.1
* Bean Validation 1.0
* Servlet 3.0

(more)
* PrettyFaces 3.3.3
* Guava 14
* Bootstrap 2.3.1

(and...)
* Eclipse IDE
* Maven 3
* JBoss AS 7
* OpenShift
* Java 7 ;)

Missing
-------

* Tests (JUnit / Arquillian?)
* Configuration script
* Change name/email/password
* Authentication with Facebook, Google, Twitter
* Token expiration
* Disabling account permanently
* Management REST API
* Email templates
* Externalize strings / I10n

Configuration
-------------

**Application**

`src/main/resources/META-INF/config.properties`

*Maven Generated* - to setup this properties, look at properties in `pom.xml`.

    cipher.key=(BASE64 key bytes)
    cipher.algorithm=(Symmetric Algorithm for Cryptography) 
    web.root=(Application Web Address for Emails)
    email.from=(From for Emails)
    email.smtp=(MailSession, supports 'default' and 'gmail') 


**JBoss AS 7**

Using JBoss AS 7.1.1.Final.

Starting from `standalone-full.xml` (replacing `standalone.xml`).

(Change enable-welcome-root to false)

    <subsystem xmlns="urn:jboss:domain:web:1.1" default-virtual-server="default-host" native="false">
        <connector name="http" protocol="HTTP/1.1" scheme="http" socket-binding="http"/>
        <virtual-server name="default-host" enable-welcome-root="false">
            <alias name="localhost"/>
        </virtual-server>
    </subsystem>


(Add EndorfinaAgencyDS replacing ExampleDS)

    <subsystem xmlns="urn:jboss:domain:datasources:1.0">
        <datasources>
            <datasource jndi-name="java:jboss/datasources/EndorfinaAgencyDS" pool-name="EndorfinaAgencyDS" enabled="true" use-java-context="true">
                <connection-url>jdbc:h2:mem:EndorfinaAgency;DB_CLOSE_DELAY=-1</connection-url>
                <driver>h2</driver>
                <security>
                    <user-name>sa</user-name>
                    <password>sa</password>
                </security>
            </datasource>
            <drivers>
                <driver name="h2" module="com.h2database.h2">
                    <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
                </driver>
            </drivers>
        </datasources>
    </subsystem>

(Add GMail Session Resource)

    <subsystem xmlns="urn:jboss:domain:mail:1.0">
        <mail-session jndi-name="java:jboss/mail/Default">
            <smtp-server outbound-socket-binding-ref="mail-smtp"/>
        </mail-session>
        <mail-session jndi-name="java:jboss/mail/GMail">
            <smtp-server ssl="true" outbound-socket-binding-ref="gmail-smtp">
                <login name="..." password="..."/>
            </smtp-server>
        </mail-session>
    </subsystem>

(Change default SMTP port)
(Add GMail SMTP outbound)

    <socket-binding-group ...>
        ...
        <outbound-socket-binding name="mail-smtp">
            <remote-destination host="localhost" port="25000"/>
        </outbound-socket-binding>
        <outbound-socket-binding name="gmail-smtp">
            <remote-destination host="smtp.gmail.com" port="465"/>
        </outbound-socket-binding>
    </socket-binding-group>

(Add EndorfinalAgencyMailQueue replacing 'testQueue' and 'testTopic')

    <subsystem xmlns="urn:jboss:domain:messaging:1.1">
        <hornetq-server>
            ...
            <jms-destinations>
                <jms-queue name="EndorfinaAgencyMailQueue">
                    <entry name="queue/EndorfinaAgencyMail"/>
                    <entry name="java:jboss/exported/jms/queue/EndorfinaAgencyMail"/>
                </jms-queue>
            </jms-destinations>
        </hornetq-server>
    </subsystem>

(Add EmailHandler MDB pool)

    <subsystem xmlns="urn:jboss:domain:ejb3:1.2">
        ...
        <pools>
            <bean-instance-pools>
                ...
                <strict-max-pool name="emailhandler-mdb-pool" max-pool-size="2" instance-acquisition-timeout="5" instance-acquisition-timeout-unit="MINUTES"/>
            </bean-instance-pools>
        </pools>
        ...
    </subsystem>

(Add EndorfinaAgency Security Domain)

    <subsystem xmlns="urn:jboss:domain:security:1.1">
        <security-domains>
            ...
            <security-domain name="EndorfinaAgency" cache-type="default">
              <authentication>
                <login-module code="Simple" flag="required" />
              </authentication>
            </security-domain>
        </security-domains>
    </subsystem>

**OpenShift**

Edit `~/app-root/data/.bash_profile`:

	APP_WEBROOT="https://${OPENSHIFT_APP_DNS}"
	APP_EMAILSMTP="gmail"
	APP_EMAILFROM="... <...@gmail.com>"
	APP_CIPHERALG="DESede"
	APP_CIPHERKEY="..."
	export APP_WEBROOT APP_EMAILSMTP APP_EMAILFROM APP_CIPHERALG APP_CIPHERKEY

Edit `<GIT CLONE>/.openshift/config/standalone.xml`:

(Same configuration as JBoss AS before, unless DS)
(still replacing ExampleDS)

    <datasource jndi-name="java:jboss/datasources/EndorfinaAgencyDS" enabled="true" use-java-context="true" pool-name="H2DS">
        <connection-url>jdbc:h2:${env.OPENSHIFT_DATA_DIR}/database;DB_CLOSE_DELAY=-1</connection-url>
        <driver>h2</driver>
        <security>
            <user-name>sa</user-name>
            <password>sa</password>
        </security>
    </datasource>

Tools
-----

**FakeSmtp**

Bind to port 25000, implements SMTP protocol and dumps to output email data.

`src/test/java/cavani/endorfina/agency/tools/FakeSmtp25000.java`

**KeyGen**

Generate Symmetric Key and dumps to output in BASE64.

`src/test/java/cavani/endorfina/agency/tools/KeyGen.java`
