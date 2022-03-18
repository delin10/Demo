package nil.ed.test.util;

/**
 * @author lidelin.
 */
public enum MemUnit {
    BYTE() {
        @Override
        public double toBytes(double num) {
            return num;
        }

        @Override
        public double toKb(double num) {
            return num / 1024.0;
        }

        @Override
        public double toMb(double num) {
            return toKb(num) / 1024.0;
        }

        @Override
        public double toGb(double num) {
            return toMb(num) / 1024.0;
        }
    },
    KB() {
        @Override
        public double toBytes(double num) {
            return num * 1024.0;
        }

        @Override
        public double toKb(double num) {
            return num;
        }

        @Override
        public double toMb(double num) {
            return num / 1024.0;
        }

        @Override
        public double toGb(double num) {
            return toMb(num) / 1024.0;
        }
    },
    MB() {
        @Override
        public double toBytes(double num) {
            return toKb(num) * 1024.0;
        }

        @Override
        public double toKb(double num) {
            return num * 1024.0;
        }

        @Override
        public double toMb(double num) {
            return num;
        }

        @Override
        public double toGb(double num) {
            return num / 1024.0;
        }
    },
    Gb() {
        @Override
        public double toBytes(double num) {
            return toKb(num) * 1024;
        }

        @Override
        public double toKb(double num) {
            return toMb(num) * 1024;
        }

        @Override
        public double toMb(double num) {
            return num * 1024.0;
        }

        @Override
        public double toGb(double num) {
            return num;
        }
    }
    ;
    public double toBytes(double num) {
        throw new UnsupportedOperationException();
    }

    public double toKb(double num) {
        throw new UnsupportedOperationException();
    }

    public double toMb(double num) {
        throw new UnsupportedOperationException();
    }

    public double toGb(double num) {
        throw new UnsupportedOperationException();
    }
}
