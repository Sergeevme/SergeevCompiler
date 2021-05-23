class More {

    public static void main(String[] args) {
        System.out.println(new Morie().breakTest());
    }
}

class Morie {

    int breakTest() {
        int i;
        while (i == 0) {
            continue;
            i = i + 1;
        }
        return 0;
    }
}
