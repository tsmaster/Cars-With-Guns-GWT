package com.bigdicegames.carswithguns.core.modes.citydrive;

import java.util.ArrayList;

import forplay.core.GroupLayer;

public class ParticleMgr {
	private static ParticleMgr singleton;
	private GroupLayer particleLayer;

	ArrayList<Particle> particleArray = new ArrayList<Particle>();

	public ParticleMgr(GroupLayer particleLayer) {
		singleton = this;
		this.particleLayer = particleLayer;
	}

	public static ParticleMgr get() {
		return singleton;
	}

	public void spawnParticle(float startX, float startY) {
		Particle particle = new Particle(startX, startY);
		particleLayer.add(particle.getLayer());
		particleArray.add(particle);
	}

	public void update(float delta) {
		for (Particle particle : particleArray) {
			particle.update(delta);
		}

		for (int i = particleArray.size() - 1; i >= 0; --i) {
			Particle particle = particleArray.get(i);
			if (!particle.isAlive()) {
				particleLayer.remove(particle.getLayer());
				particleArray.remove(particle);
			}
		}

	}

}
