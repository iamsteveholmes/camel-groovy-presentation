import org.apache.camel.language.groovy.GroovyRouteBuilder
import org.apache.camel.impl.DefaultCamelContext

@Grab(group = 'org.apache.camel', module = 'camel-groovy', version = '2.3.0')
@Grab(group = 'org.apache.camel', module = 'camel-xmpp', version = '2.3.0')
@Grab(group = 'org.apache.camel', module = 'camel-core', version = '2.3.0')
@Grab(group = 'org.apache.camel', module = 'camel-test', version = '2.3.0')
@Grab(group = 'junit', module = 'junit', version = '4.4')
/**
 * For use by Castle Press LLC or anyone else 
 * who gets their grubby little hands on it.
 *
 * User: steve
 * Date: Jun 8, 2010
 * Time: 2:42:53 PM
 */

class SampleRoute extends GroovyRouteBuilder {


  protected void configure() {

    def props = new Properties()
    new File("/tmp/props.properties").withInputStream {
      stream -> props.load(stream)
    }
    
    from("direct://start").
            to("xmpp://talk.google.com:5222/iamsteveholmes@gmail.com?serviceName=gmail.com&user=${props.username}&password=${props.password}")
  }
}

camelCtx = new DefaultCamelContext()

camelCtx.addRoutes(new SampleRoute())
camelCtx.start()

template = camelCtx.createProducerTemplate()
template.sendBody "direct:start", "Sup Bro!"
