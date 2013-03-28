Endorfina Agency
================

Web Application for intelligent system operation.

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

Not Implemented: token expiration, change email/password, change name, disabling account permanently

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
* JAAS

(more)
* PrettyFaces 3.3.3
* Guava 0.14
* Bootstrap 2.3.1

(and...)
* Maven 3 ;)

Missing
-------

* Tests (JUnit / Arquillian?)
* Externalize strings / I10n
* Facebook, Google, Twitter authentication
* REST Management API

Configuration
-------------

Application configuration:

`src/main/resources/META-INF/config.properties`

    cipher.key=(BASE64 key bytes)
    cipher.algorithm=(Symmetric Algorithm for Cryptography) 
    web.root=(Application Web Address for Emails)
    email.from=(From for Emails)
    email.smtp=(MailSession, supports 'default' and 'gmail') 

JBoss AS 7 configuration:

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
