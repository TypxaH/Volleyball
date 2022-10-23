public class VolleyballPlayer implements Playable{
    protected String name;
    protected int age;
    protected String position;
    protected byte skills;
    protected byte condition;

    public VolleyballPlayer(String name, int age, String position, byte skills, byte condition) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.skills = skills;
        this.condition = condition;
    }

    //метод train(), който увеличава уменията му с 1 и намалява кондицията с 1.
    //Ако кондицията е 1 - не може да тренира.
    public void train(){
        if (this.condition != 1) {
            if ( this.skills < 10) {
                this.skills++;
            }
            this.condition--;
        }
    }
    //метод rest(), който увеличава кондицията му с 1.
    public void rest(){
        if (this.condition < 5) {
            this.condition++;
        }
    }

    @Override
    public String toString() {
        return "VolleyballPlayer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                ", skills=" + skills +
                ", condition=" + condition +
                '}';
    }
}