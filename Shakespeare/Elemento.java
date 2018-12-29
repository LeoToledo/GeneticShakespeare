class Elemento{
    private String dna = new String();
    private double fitness;

    public String getDna()
    {
        return this.dna;
    }
    public double getFitness()
    {
        return this.fitness;
    }
    public void setDna(String gen)
    {
        this.dna = gen;
    }
    public void setFitness(double fit)
    {
        this.fitness = fit;
    }
}