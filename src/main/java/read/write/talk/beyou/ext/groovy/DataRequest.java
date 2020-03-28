package read.write.talk.beyou.ext.groovy;

public class DataRequest {
        private static final long serialVersionUID = 887735804547394666L;

        private String dataGroup;
        private DomainInfo domainInfo;
        private String additional;

        public String getDataGroup() {
            return dataGroup;
        }

        public void setDataGroup(String dataGroup) {
            this.dataGroup = dataGroup;
        }

        public DomainInfo getDomainInfo() {
            return domainInfo;
        }

        public void setDomainInfo(DomainInfo domainInfo) {
            this.domainInfo = domainInfo;
        }

        public String getAdditional() {
            return additional;
        }

        public void setAdditional(String additional) {
            this.additional = additional;
        }
}
