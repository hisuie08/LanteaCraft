package lc.api.components;

import lc.api.defs.IContainerDefinition;

/**
 * Definition registry interface
 *
 * @author AfterLifeLochie
 *
 */
public interface IDefinitionRegistry {

	/**
	 * Add a new game definition
	 *
	 * @param definition
	 *            The definition element
	 */
	public void addDefinition(IContainerDefinition definition);

	/**
	 * Get a named definition from this registry
	 *
	 * @param name
	 *            The definition name
	 * @return The definition or null if it does not exist
	 */
	public IContainerDefinition getDefinition(String name);

}
